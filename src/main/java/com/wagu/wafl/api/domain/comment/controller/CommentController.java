package com.wagu.wafl.api.domain.comment.controller;

import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.common.message.ResponseMessage;
import com.wagu.wafl.api.config.resolver.UserId;
import com.wagu.wafl.api.domain.comment.dto.request.CreateCommentReplyDTO;
import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;
import com.wagu.wafl.api.domain.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation( summary = "게시글에 댓글 달기", description = "게시글에 댓글을 답니다.")
    @PostMapping("")
    public ResponseEntity<ApiResponse> createPostComment(
            @UserId Long userId, // 토큰으로 수정
            @Valid @RequestBody CreatePostCommentDTO request) {
        commentService.createPostComment(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_POST_COMMENT.getMessage()));
    }

    @Operation( summary = "댓글에 답댓글 달기", description = "댓글에 답댓글을 답니다.")
    @PostMapping("/reply")
    public ResponseEntity<ApiResponse> createCommentReply(
            @UserId Long userId,
            @Valid @RequestBody CreateCommentReplyDTO request) {
        commentService.createCommentReply(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_COMMENT_REPLY.getMessage()));
    }

}
