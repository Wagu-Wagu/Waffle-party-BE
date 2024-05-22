package com.wagu.wafl.api.domain.post.controller;

import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.common.message.ResponseMessage;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation( summary = "OTT 포스트 리스트 갖고오기 ",
            description = "OTT필터링에 맞는 OTT포스트를 시간 순을 기준으로 나열합니다."
    )
    @GetMapping("")
    public ResponseEntity<ApiResponse> getOttPosts(@RequestParam(required = false, defaultValue = "") List<OttTag> ottTags) {
        val response = postService.getOttPosts(ottTags);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_POST_LIST.getMessage(), response));
    }
}
