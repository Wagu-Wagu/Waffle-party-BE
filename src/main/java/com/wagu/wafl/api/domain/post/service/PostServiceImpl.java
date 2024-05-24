package com.wagu.wafl.api.domain.post.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wagu.wafl.api.common.exception.PostException;
import com.wagu.wafl.api.common.exception.UserException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.config.S3Config;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.comment.repository.CommentRepository;
import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.dto.response.PostDetailResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import com.wagu.wafl.api.domain.s3.service.S3Service;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

        String[] stringArray = imageUrls.replaceAll("[\\[\\]\"]", "").split(",");
        List<String> imageUrlList = Arrays.asList(stringArray);

        postRepository.save(Post.builder()
                .user(user)
                .ottTag(request.ottTag())
                .title(request.title())
                .content(request.content())
                .photoes(imageUrls)
                .thumbNail(imageUrlList.get(0))
                .build());
    }

    @Override
    public PostDetailResponseDTO getPostDetail(Long userId, Long postId) {
        findPost(postId);

        // todo - 게시글을 올린유저가 삭제되면 어떻게 보일지
        // todo - comment 올린 유저가 삭제되면 어떻게 보일지
        // todo - 댓글, 답댓글이 삭제되면 어떻게 보일지
        /**
         * 1. 게시글에 관련된 코멘트들 갖고오기
         * 2. 코맨트 관련 '보이는 여부'
         */
        List<Comment> resultComments = new ArrayList<>();
        List<Comment> parentComments = commentRepository.findAllCommentByPostId(postId);

        for(Comment parentComment : parentComments) { //todo - 성능저하 가능성, isActive여부
            List<Comment> subComments = new ArrayList<>();
            subComments.add(parentComment);
            parentComment.getSubComments().sort(Comparator.comparing(Comment::getCreatedAt));
            subComments.addAll(parentComment.getSubComments());
            resultComments.addAll(subComments);
        }
        for(Comment comment : resultComments) {
            System.out.println(comment.getId());
            System.out.println(comment.getParentComment()==null);
            System.out.println(comment.getContent());
            System.out.println(comment.getCreatedAt());
        }
        /**
         * 1. 게시글에 관련된 코멘트들 갖고오기
         * 2. 코맨트 관련 '보이는 여부'
         * parent댓글만 갖고오고, parent댓글 리스트들 뿌리기도 괜찮을듯,
         */
        return null;

    }

    private String savePostImagesAndGetUrl(List<MultipartFile> postImages) {
        if (checkImageFilesEmpty(postImages)) {
            return s3Service.uploadImages(postImages, s3Config.getPostImageFolderName());
        }

        return "";
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
