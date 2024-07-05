package com.wagu.wafl.api.domain.user.controller;


import com.wagu.wafl.api.config.resolver.UserId;
import com.wagu.wafl.api.domain.user.dto.request.OnboardRequestDTO;
import com.wagu.wafl.api.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import lombok.val;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.common.message.ResponseMessage;
import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "UserController", description = "UserController 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation( summary = "유저 온보딩", description = "유저 온보딩을 진행합니다.")
    @PatchMapping("/onboard")
    public ResponseEntity<ApiResponse> onboard(@UserId Long userId, @Valid @RequestBody OnboardRequestDTO request) {
        userService.onboard(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_USER_ONBOARD.getMessage()));
    }

    @Operation( summary = "유저 닉네임 변경",
            description = "닉네임 유효성 체크 후 유저 닉네임을 변경합니다."
    )
    @PatchMapping("/nickname")
    ResponseEntity<ApiResponse> editUserNickName(@UserId Long userId,
                                                 @Valid @RequestBody EditUserNickNameRequestDto request) {
        userService.editUserNickName(userId, request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_EDIT_USER_NICKNAME.getMessage()));
    }

    @Operation( summary = "유저 프로필 사진 변경",
            description = "유저 프로필 사진을 변경합니다. 최대 파일 크기 2MB"
    )
    @PatchMapping("/image")
    ResponseEntity<ApiResponse> editUserImage(@UserId Long userId,
                                                 @RequestPart(required = false) MultipartFile userImage) {
        userService.editUserImage(userId, userImage);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_EDIT_USER_PHOTO.getMessage()));
    }

    @Operation( summary = "마이페이지 api",
        description = "마이페이지 관련 정보를 return합니다."
    )
    @GetMapping("/my")
    ResponseEntity<ApiResponse> getMyInfo(@UserId Long userId){
        val response = userService.getMyInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_INFO.getMessage(), response));
    }

    @Operation( summary = " 유저가 단 댓글 확인 api",
    description = "유저가 올린 댓글, 답댓글 정보를 return 합니다. 최신순으로 정렬")
    @GetMapping("/my/comments")
    ResponseEntity<ApiResponse> getMyComments(@UserId Long userId) {
        val response = userService.getMyComments(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_COMMENTS.getMessage(), response));
    }

    @Operation( summary = "유저가 작성한 글 확인 api",
            description = "유저가 작성한 글 정보를 return 합니다. 최신순으로 정렬")
    @GetMapping("/my/posts")
    ResponseEntity<ApiResponse> getMyPosts(@UserId Long userId) {
        val response = userService.getMyPosts(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_POSTS.getMessage(), response));
    }

    @Operation( summary = "온보딩 여부 확인 api",
            description = "온보딩을 한 유저인지 아닌 유저인지 return 합니다.")
    @GetMapping("/isOnboard")
    ResponseEntity<ApiResponse> isOnboard(@UserId Long userId) {
        val response = userService.isOnboard(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_IS_ONBOARD.getMessage(), response));
    }

    @Operation( summary = "내 소식 조회 api", description = "내 소식, 알람 정보들을 조회합니다.")
    @GetMapping("/my/news")
    ResponseEntity<ApiResponse> myNews(@UserId Long userId) {
        val response = userService.getMyNews(userId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_MY_NEWS.getMessage(), response));
    }

    @Operation( summary = "알람 생긴 글 조회 api", description = "새 알람을 확인합니다.")
    @GetMapping("/my/news/{alertId}")
    ResponseEntity<ApiResponse> checkMyNews(@UserId Long userId, @PathVariable Long alertId) {
        userService.checkMyNews(userId, alertId);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_CHECK_MY_NEWS.getMessage()));
    }
}
