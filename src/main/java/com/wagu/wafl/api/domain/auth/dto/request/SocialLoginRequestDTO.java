package com.wagu.wafl.api.domain.auth.dto.request;

public record SocialLoginRequestDTO(
        String token,
        String providerType
) {
}
