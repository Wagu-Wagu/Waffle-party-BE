package com.wagu.wafl.api.domain.post.controller;

import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.common.message.ResponseMessage;
import com.wagu.wafl.api.config.resolver.UserId;
import com.wagu.wafl.api.domain.post.dto.request.CreatePostRequestDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;

import com.wagu.wafl.api.domain.s3.service.S3Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation( summary = "글 작성하기 ",
            description = "글을 작성합니다.."
    )
    @PostMapping("")
    public ResponseEntity<ApiResponse> createPost(
            @UserId Long userId,
            @ModelAttribute @Valid CreatePostRequestDTO request
            ) {
        postService.createPost(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CREATE_POST.getMessage()));
    }


    @Operation( summary = "OTT 포스트 리스트 갖고오기 ",
            description = "OTT필터링에 맞는 OTT포스트를 시간 순을 기준으로 나열합니다."
    )
    @GetMapping("")
    public ResponseEntity<ApiResponse> getOttPosts(@RequestParam(required = false, defaultValue = "") List<OttTag> ottTags) {
        val response = postService.getOttPosts(ottTags);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POST_LIST.getMessage(), response));
    }


    @Operation( summary = "게시글 상세 정보 조회",
            description = "게시글 내용 및 댓글 정보를 조회합니다."
    )
    @GetMapping("/detail/{postId}")
    public ResponseEntity<ApiResponse> getPostDetail(@RequestHeader(value = "Authorization", required = false) String accessToken, @PathVariable Long postId) { //@UserId Long userId
        val response = postService.getPostDetail(accessToken, postId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POST_DETAIL.getMessage(), response));
    }
}
