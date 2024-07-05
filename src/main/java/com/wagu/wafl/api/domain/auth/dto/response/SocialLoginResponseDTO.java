package com.wagu.wafl.api.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record SocialLoginResponseDTO(
        boolean isProfileCompleted,
        String accessToken
) {
    public static SocialLoginResponseDTO of(String accessToken, boolean isProfileCompleted) {
        return SocialLoginResponseDTO.builder()
                .isProfileCompleted(isProfileCompleted)
                .accessToken(accessToken)
                .build();
    }
}
