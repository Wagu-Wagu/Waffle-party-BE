package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import com.wagu.wafl.api.domain.user.dto.response.GetMyInfoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void editUserNickName(Long userId, EditUserNickNameRequestDto request);
    void editUserImage(Long userId, MultipartFile requestFile);
    GetMyInfoResponseDTO getMyInfo(Long userId);

}
