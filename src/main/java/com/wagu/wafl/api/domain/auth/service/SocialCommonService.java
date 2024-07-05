package com.wagu.wafl.api.domain.auth.service;

import com.wagu.wafl.api.domain.auth.dto.response.SocialLoginResponseDTO;
import com.wagu.wafl.api.domain.auth.kakao.response.KakaoUserResponse;

public interface SocialCommonService {

    SocialLoginResponseDTO waguLogin(KakaoUserResponse kakaoUserResponse, String providerType);
}
