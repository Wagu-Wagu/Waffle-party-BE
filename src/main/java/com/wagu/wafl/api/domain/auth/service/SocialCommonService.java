package com.wagu.wafl.api.domain.auth.service;

import com.wagu.wafl.api.domain.auth.dto.response.SocialLoginResponseDTO;

public interface SocialCommonService {

    SocialLoginResponseDTO waguLogin(String thirdPartUserId, String providerType);
}
