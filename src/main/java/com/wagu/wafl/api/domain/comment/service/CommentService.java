package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.domain.comment.dto.request.CreateCommentReplyDTO;
import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;
import com.wagu.wafl.api.domain.comment.dto.request.EditCommentRequestDTO;

public interface CommentService {
    void createPostComment(Long userId, CreatePostCommentDTO request);
    void createCommentReply(Long userId, CreateCommentReplyDTO request);
    void editComment(Long userId, EditCommentRequestDTO request);
    void deleteComment(Long userId, Long commentId);
}
