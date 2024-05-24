package com.wagu.wafl.api.domain.post.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wagu.wafl.api.common.exception.PostException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import com.wagu.wafl.api.domain.s3.service.S3Service;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    @Override
    public List<OttPostsListResponseDTO> getOttPosts(List<OttTag> request) {

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
        String imageUrls = savePostImagesAndGetUrl(request.postImages()); // S3에 저장 후 url list를 리턴 받는다.

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

    private String savePostImagesAndGetUrl(List<MultipartFile> postImages) {
        MultipartFile multipartFile = postImages.get(0);
        String originFileName = multipartFile.getOriginalFilename();

        if (!postImages.isEmpty() && originFileName != "") {
            return s3Service.uploadImages(postImages, "photos");
        }

        return "";
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PostException(ExceptionMessage.NOT_FOUND_USER.getMessage(), HttpStatus.NOT_FOUND));
    }
}
