package com.wagu.wafl.api.domain.user.service;


import com.wagu.wafl.api.domain.auth.kakao.KakaoApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final KakaoApiClient kakaoApiClient;

    @Transactional
    @Override
    public void editUserNickName(EditUserNickNameRequestDto request) {

    }

}
