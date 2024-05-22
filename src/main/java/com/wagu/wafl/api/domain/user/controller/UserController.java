package com.wagu.wafl.api.domain.user.controller;

import com.wagu.wafl.api.domain.user.dto.request.UserLoginRequestDTO;
import com.wagu.wafl.api.domain.user.kakao.dto.response.KakaoUserResponse;
import com.wagu.wafl.api.domain.user.service.UserService;
import com.wagu.wafl.api.domain.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController", description = "UserController 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("kakao-test")
    public KakaoUserResponse kakaoTest(@RequestBody UserLoginRequestDTO request) {
        val response = userService.login(request);

        return response;
    }
}
