package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;

public interface CommentService {
    void createPostComment(Long userId, CreatePostCommentDTO request);
}
