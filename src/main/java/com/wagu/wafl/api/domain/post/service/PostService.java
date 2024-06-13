package com.wagu.wafl.api.domain.post.service;

import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.request.EditPostRequestDTO;
import com.wagu.wafl.api.domain.post.dto.request.UploadPostImageRequestDTO;
import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.dto.response.PostDetailResponseDTO;
import com.wagu.wafl.api.domain.post.dto.response.UploadPostImageResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;

import java.util.List;

public interface PostService {

    List<OttPostsListResponseDTO> getOttPosts(List<OttTag> request);
    void createPost(Long userId, CreatePostRequestDTO createPostRequestDTO);
    PostDetailResponseDTO getPostDetail(String accessToken, Long postId);
    void editPost(Long userId, EditPostRequestDTO request);
    UploadPostImageResponseDTO uploadPostImages(UploadPostImageRequestDTO request);
}
