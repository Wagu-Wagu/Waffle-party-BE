package com.wagu.wafl.api.domain.auth.kakao;

import com.wagu.wafl.api.common.exception.AuthException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.domain.auth.dto.request.SocialLoginRequestDTO;
import com.wagu.wafl.api.domain.auth.dto.response.SocialLoginResponseDTO;
import com.wagu.wafl.api.domain.auth.kakao.response.KakaoUserResponse;
import com.wagu.wafl.api.domain.auth.service.SocialCommonService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoSocialService {
    private final KakaoApiClient kakaoApiClient;
    private final SocialCommonService socialCommonService;

    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        KakaoUserResponse userResponse;
        try{
            userResponse = kakaoApiClient.getUserInformation("Bearer " + request.token());
        } catch (FeignException feignException) {
            throw new AuthException(ExceptionMessage.INVALID_KAKAO_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return socialCommonService.waguLogin(userResponse.id(), request.providerType());
    }
}
