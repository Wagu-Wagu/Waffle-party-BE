package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Transactional
    @Override
    public void editUserNickName(EditUserNickNameRequestDto request) {

    }
}
