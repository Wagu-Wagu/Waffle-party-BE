package com.wagu.wafl.api.domain.social.kakao;

import com.wagu.wafl.api.domain.social.kakao.response.KakaoLoginRequestDTO;
import com.wagu.wafl.api.domain.social.kakao.response.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoSocialService {
    private final KakaoApiClient kakaoApiClient;

    public KakaoUserResponse login(KakaoLoginRequestDTO request) {
        return kakaoApiClient.getUserInformation("Bearer " + request.token());
    }
}
