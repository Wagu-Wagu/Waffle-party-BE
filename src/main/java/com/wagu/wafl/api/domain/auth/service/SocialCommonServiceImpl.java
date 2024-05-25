package com.wagu.wafl.api.domain.auth.service;

import com.wagu.wafl.api.config.jwt.JwtTokenManager;
import com.wagu.wafl.api.domain.auth.dto.response.SocialLoginResponseDTO;
import com.wagu.wafl.api.domain.auth.kakao.response.KakaoUserResponse;
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
            KakaoUserResponse kakaoUserResponse,
            String providerType
    ) {
        AuthProvider authProvider = authProviderRepository.save(
                AuthProvider.builder() // todo - 이유 체크
                .id(kakaoUserResponse.id())
                .providerType(providerType)
                .build());
        authProviderRepository.save(authProvider);

        val user = userRepository.save(
                User.builder()
                        .email(kakaoUserResponse.kakao_account().getEmail())
                        .authProvider(authProvider)
                        .build());

        val accessToken = jwtTokenManager.createAccessToken(user.getId());

        return SocialLoginResponseDTO.of(accessToken, false);
    }

    private SocialLoginResponseDTO reLogin(User user) {
        val accessToken = jwtTokenManager.createAccessToken(user.getId());
        if (Objects.equals(user.getNickName(), null)) {
            return SocialLoginResponseDTO.of(accessToken, false);
        }
        return SocialLoginResponseDTO.of(accessToken, true);
    }

    @Transactional
    @Override
    public SocialLoginResponseDTO waguLogin(KakaoUserResponse kakaoUserResponse, String providertype) {
        val authProvider = authProviderRepository.searchAuthProviderById(kakaoUserResponse.id());

        if (authProvider.isPresent()) {
            return reLogin(authProvider.get().getUser());
        }

        return SignUpAndLogin(kakaoUserResponse, providertype);
    }
}