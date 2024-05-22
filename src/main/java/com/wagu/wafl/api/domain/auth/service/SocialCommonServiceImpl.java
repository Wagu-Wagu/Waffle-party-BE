package com.wagu.wafl.api.domain.auth.service;

import com.wagu.wafl.api.config.jwt.JwtTokenManager;
import com.wagu.wafl.api.domain.auth.dto.response.SocialLoginResponseDTO;
import com.wagu.wafl.api.domain.user.entity.AuthProvider;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.AuthProviderRepository;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SocialCommonServiceImpl implements SocialCommonService {
    private final AuthProviderRepository authProviderRepository;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

    private SocialLoginResponseDTO SignUpAndLogin(
            String thirdPartyUserId,
            String providerType
    ) {
        val user = userRepository.save(User.builder().build());
        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);

        authProviderRepository.save(AuthProvider.builder()
                .id(thirdPartyUserId)
                .providerType(providerType)
                .user(user)
                .build());

        return SocialLoginResponseDTO.of(accessToken);
    }

    @Transactional
    private SocialLoginResponseDTO reLogin(User user) {
        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);

        return SocialLoginResponseDTO.of(accessToken);
    }

    @Override
    public SocialLoginResponseDTO waguLogin(String thirdPartUserId, String providertype) {
        val authProvider = authProviderRepository.searchAuthProviderById(thirdPartUserId);

        if (Objects.nonNull(authProvider)) {
            return reLogin(authProvider.getUser());
        }

        return SignUpAndLogin(thirdPartUserId, providertype);
    }
}
