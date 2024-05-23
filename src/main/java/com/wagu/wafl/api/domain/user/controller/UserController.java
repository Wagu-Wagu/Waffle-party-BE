package com.wagu.wafl.api.domain.user.controller;


import com.wagu.wafl.api.config.resolver.UserId;
import com.wagu.wafl.api.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

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

    @Operation( summary = "유저 닉네임 변경",
            description = "닉네임 유효성 체크 후 유저 닉네임을 변경합니다."
    )
    @PatchMapping("/nickName")
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
}
