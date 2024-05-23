package com.wagu.wafl.api.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(
        String accessToken
) {
    public static SocialLoginResponseDTO of(String accessToken) {
        return SocialLoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
