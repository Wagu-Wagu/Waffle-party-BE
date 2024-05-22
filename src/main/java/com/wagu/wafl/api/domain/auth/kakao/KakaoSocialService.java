package com.wagu.wafl.api.domain.social.kakao;

import com.wagu.wafl.api.domain.social.dto.request.SocialLoginRequestDTO;
import com.wagu.wafl.api.domain.social.dto.response.SocialLoginResponseDTO;
import com.wagu.wafl.api.domain.social.kakao.response.KakaoUserResponse;
import com.wagu.wafl.api.domain.social.service.SocialCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoSocialService {
    private final KakaoApiClient kakaoApiClient;
    private final SocialCommonService socialCommonService;

    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation("Bearer " + request.token());

        return socialCommonService.waguLogin(userResponse.id());
    }
}
