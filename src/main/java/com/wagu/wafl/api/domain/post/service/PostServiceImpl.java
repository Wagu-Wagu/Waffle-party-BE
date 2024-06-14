package com.wagu.wafl.api.domain.post.service;

import com.wagu.wafl.api.common.exception.PostException;
import com.wagu.wafl.api.common.exception.UserException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.config.S3Config;
import com.wagu.wafl.api.config.jwt.JwtTokenManager;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.comment.repository.CommentRepository;
import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.request.EditPostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.request.UploadPostImageRequestDTO;
import com.wagu.wafl.api.domain.post.dto.response.PostDetailCommentVO;
import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.dto.response.PostDetailResponseDTO;
import com.wagu.wafl.api.domain.post.dto.response.UploadPostImageResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import com.wagu.wafl.api.domain.s3.service.S3Service;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final CommentRepository commentRepository;
    private final JwtTokenManager jwtTokenManager;
    private final Long IS_NOT_SERVICE_USER = -1L;
    private final int MAX_POST_IMAGES_COUNT = 3;

    @Override
    public List<OttPostsListResponseDTO> getOttPosts(List<OttTag> request) {
        // todo - 게시글을 올린유저가 삭제되면 어떻게 보일지
        if(request.isEmpty()) {
            return postRepository.findAllByCreatedAtAndIsActive().stream()
                    .map((p) -> OttPostsListResponseDTO.of(p,
                            p.getUser().getNickName()))
                    .toList();
        }

        return postRepository.findAllByOttTagIn(request).stream()
                .map((p) -> OttPostsListResponseDTO.of(p,
                        p.getUser().getNickName()))
                .toList();
    }

    @Override
    public void createPost(Long userId, CreatePostRequestDTO request) {
        User user = findUser(userId);
        String imageUrls = savePostImagesAndGetUrl(request.postImages());

        String thumbNail = getThumbNail(imageUrls);

        postRepository.save(Post.builder()
                .user(user)
                .ottTag(request.ottTag())
                .title(request.title())
                .content(request.content())
                .photoes(imageUrls)
                .thumbNail(thumbNail)
                .build());
    }

    // todo - 게시글을 올린유저가 삭제되면 어떻게 보일지
    // todo - comment 올린 유저가 삭제되면 어떻게 보일지
    // todo - 댓글, 답댓글이 삭제되면 어떻게 보일지
    @Override
    public PostDetailResponseDTO getPostDetail(String accessToken, Long postId) {
        Post post = findPost(postId);
        validateActivePost(post);

        List<Comment> resultComments = new ArrayList<>();
        List<Comment> parentComments = commentRepository.findAllCommentByPostId(postId);

        for (Comment parentComment : parentComments) { //todo - 성능저하 가능성, isActive여부
            List<Comment> subComments = new ArrayList<>();

            if(parentComment.getSubComments().size()==0 && !parentComment.getIsActive()) {
                continue;
            }
            subComments.add(parentComment);
            parentComment.getSubComments().sort(Comparator.comparing(Comment::getCreatedAt));
            subComments.addAll(parentComment.getSubComments());
            resultComments.addAll(subComments);
        }

        Long userId = verifyServiceUser(accessToken);

        // validate IsVisible
        List<PostDetailCommentVO> commentVOs = isVisibleSecretComment(post.getUser().getId(), userId, resultComments);
        return PostDetailResponseDTO.of(post, Objects.equals(userId, post.getUser().getId()), commentVOs);
        }

    @Transactional
    @Override
    public void editPost(Long userId, EditPostRequestDTO request) {
        Post post = findPost(request.postId());
        validatePostOwner(userId, post);

        String thumbNail = "";
        String imageUrls = "";
        if(!request.postImages().isEmpty()) {
            thumbNail = getThumbNail(request.postImages().get(0));
            imageUrls = request.postImages().stream()
                    .map(s3Url -> '"'+s3Url+'"')
                    .collect(Collectors.joining(",","[","]"));
        }

        post.setTitle(request.title());
        post.setContent(request.content());
        post.setPhotoes(imageUrls);
        post.setOttTag(request.ottTag());
        post.setThumbNail(thumbNail);
    }

    public void validateActivePost(Post post) {
        if(!post.getIsActive()) {
            throw new PostException(ExceptionMessage.NOT_ACTIVE_POST.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public UploadPostImageResponseDTO uploadPostImages(UploadPostImageRequestDTO request) {
        if(request.postImages().size() >= MAX_POST_IMAGES_COUNT) {
            throw new PostException(ExceptionMessage.EXCEED_MAX_FILE_COUNT.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if(!checkImageFilesEmpty(request.postImages())) {
            throw new PostException(ExceptionMessage.IS_EMPTY_FILE.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String images = savePostImagesAndGetUrl(request.postImages());

        return UploadPostImageResponseDTO.of(images);
    }

    private Long verifyServiceUser(String accessToken) {
        if(accessToken!=null) {
            return  jwtTokenManager.getUserIdFromJwt(accessToken);
        }
        return IS_NOT_SERVICE_USER;
    }

    private void validatePostOwner(Long userId, Post post) {
        if(!(post.getUser().getId() == userId)) {
            throw new PostException(ExceptionMessage.IS_NOT_POST_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    List<PostDetailCommentVO> isVisibleSecretComment(Long postUserId, Long tokenUserId, List<Comment> comments) {
        if (Objects.equals(tokenUserId, IS_NOT_SERVICE_USER)) { // todo => 서비스 미가입자
            return comments.stream().map(PostDetailCommentVO::noAccessSecretComment).toList();
        }

        if (Objects.equals(tokenUserId, postUserId)) { // 글 쓴 사람 일 경우 , 어드민 유저일 경우
            return comments.stream().map(
                            (c) -> PostDetailCommentVO.fullAccessSecretComment(c, Objects.equals(c.getUser().getId(), tokenUserId)))
                    .toList();
        }

        List<PostDetailCommentVO> commentVOS = new ArrayList<>();
        for(Comment comment : comments) {
            PostDetailCommentVO commentVO;

            if(comment.getIsSecret()) { //비밀 댓글이라면

                if(Objects.equals(comment.getUser().getId(), tokenUserId))  { // 내가 쓴 비밀댓글이라면
                    commentVO = PostDetailCommentVO.isVisibleSecretComment(comment, true, true);
                }
               else if (isParentCommentUser(comment, tokenUserId)) { // 내가 작성한 비밀 (답)댓글이 아니지만, 내가 쓴 댓글의 비밀 답댓글일 경우
                    commentVO = PostDetailCommentVO.isVisibleSecretComment(comment, false, true);
                }
                else{
                    // 비밀 댓글인데, 내가 볼 수 없는 경우
                    commentVO = PostDetailCommentVO.isVisibleSecretComment(comment, false, false);
                }
            }
            else{ // 비밀 댓글이 아니라면
                commentVO = PostDetailCommentVO.isVisibleSecretComment(comment, Objects.equals(comment.getUser().getId(), tokenUserId), true);
            }
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }

    private boolean isParentCommentUser(Comment comment, Long tokenUserId) { // 내가 쓴 댓글
        return comment.getParentComment() != null // 답댓글인지 판단
                && Objects.equals(comment.getParentComment().getUser().getId(), tokenUserId); // 내가 쓴 댓글의 답댓글일경우
    }
    /*
         비밀 댓글인 경우
         글 작성자
         비밀 댓글 작성자 본인
         admin user

         비밀 답댓글인 경우
         글 작성자
         비밀 답댓글 작성자 본인
         해당 답댓글이 달린 댓글 작성자
         admin user

         비밀 댓글일 경우 ,
         if (유저 ID가 없을 경우) {
         모든 댓글이 안보임
         }

         글 작성자 , admin유저,
         if ( userId == post.getUser().getId() || user.Role==ADMIN ) {
            모든 비밀 댓글 isVisible == true
         }

         글 작성자는 아니지만, 비밀 댓글을 쓴 사람일 경우
         if( userId != post.getUser().getId() && commnet.getParentCommnet()==null && comment.getUser().getId() == userId ) {
            댓글, 답댓글 모두 isVisible == true
         }

         글 작성자는 아니지만, 비밀 답댓글을 쓴 사람 일 경우
        if(userId!=post.getUser().getId() && commnet.getParentCommnet()!=null && commnet.getUser().getId() == userId) {
           답댓글에 해당하는 거만 isVisible == true

         */


        /* 경우의 수
         A 댓글
         B 비밀답댓글 볼 수 있는 사람 : A, B
         C 비밀 답댓글 : 볼 수 있는 사람 A, C

         M Post
         C 댓글
         A답댓글
         B 비밀 답댓글 => 볼 수 있는 사람 : C, M, B


         A Post
         C 댓글
         A답댓글
         B 비밀 답댓글 => 볼 수 있는 사람 : C, A, B

         */

    @Transactional
    @Override
    public void deletePost(Long userId, Long postId) {
        User user = findUser(userId);
        Post post = findPost(postId);

        validatePostOwner(userId, post);

        post.setIsActive(false);
    }

    private String savePostImagesAndGetUrl(List<MultipartFile> postImages) {
        if (checkImageFilesEmpty(postImages)) {
            return s3Service.uploadImages(postImages, s3Config.getPostImageFolderName());
        }

        return "";
    }
    private String getThumbNail(String imageUrls) {
        String[] stringArray = imageUrls.replaceAll("[\\[\\]\"]", "").split(",");
        List<String> imageUrlList = Arrays.asList(stringArray);
        return imageUrlList.get(0);
    }

    private boolean checkImageFilesEmpty(List<MultipartFile> postImages) {
        MultipartFile multipartFile = postImages.get(0);
        String originFileName = multipartFile.getOriginalFilename();

        if (Objects.equals(originFileName, "")) {
            return false;
        }
        return true;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionMessage.NOT_FOUND_USER.getMessage(), HttpStatus.NOT_FOUND));
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_POST.getMessage()));
    }
}
