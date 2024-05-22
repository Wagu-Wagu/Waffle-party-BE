package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.user.dto.request.UserLoginRequestDTO;
import com.wagu.wafl.api.domain.user.kakao.KakaoApiClient;
import com.wagu.wafl.api.domain.user.kakao.dto.response.KakaoUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final KakaoApiClient kakaoApiClient;

    public KakaoUserResponse login(UserLoginRequestDTO token) {
        return kakaoApiClient.getUserInformation("Bearer " + token.accessToken());
    }


}
