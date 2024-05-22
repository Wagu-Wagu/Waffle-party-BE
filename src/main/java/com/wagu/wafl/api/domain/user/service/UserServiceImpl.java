package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.social.kakao.response.KakaoLoginRequestDTO;
import com.wagu.wafl.api.domain.user.dto.request.UserLoginRequestDTO;
import com.wagu.wafl.api.domain.social.kakao.KakaoApiClient;
import com.wagu.wafl.api.domain.social.kakao.response.KakaoUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    @Transactional
    @Override
    public void editUserNickName(EditUserNickNameRequestDto request) {

    }

}
