package com.wagu.wafl.api.domain.social.controller;

import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.domain.social.kakao.KakaoSocialService;
import com.wagu.wafl.api.domain.social.kakao.response.KakaoLoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social")
public class SocialController {
    private final KakaoSocialService kakaoSocialService;
    @Operation( summary = "소셜 로그인",
            description = "소셜 로그인을 진행합니다."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody KakaoLoginRequestDTO request){
        val response = kakaoSocialService.login(request);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN.getMessage(), response));
    }
}