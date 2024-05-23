package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;

public interface UserService {

    void editUserNickName(Long userId, EditUserNickNameRequestDto request);

}
