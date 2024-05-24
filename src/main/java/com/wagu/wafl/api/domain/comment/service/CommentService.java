package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.domain.comment.dto.request.CreateCommentReplyDTO;
import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;

public interface CommentService {
    void createPostComment(Long userId, CreatePostCommentDTO request);
    void createCommentReply(Long userId, CreateCommentReplyDTO request);
}
