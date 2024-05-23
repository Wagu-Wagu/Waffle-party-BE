package com.wagu.wafl.api.domain.post.service;

import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<OttPostsListResponseDTO> getOttPosts(List<OttTag> request);
    void createPost(Long userId, CreatePostRequestDTO createPostRequestDTO);
}
