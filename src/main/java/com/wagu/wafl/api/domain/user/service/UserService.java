package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import com.wagu.wafl.api.domain.user.dto.request.OnboardRequestDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyCommentResponseDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyInfoResponseDTO;
import java.util.List;

import com.wagu.wafl.api.domain.user.dto.response.GetMyPostResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void onboard(Long userId, OnboardRequestDTO request);
    void editUserNickName(Long userId, EditUserNickNameRequestDto request);
    void editUserImage(Long userId, MultipartFile requestFile);
    GetMyInfoResponseDTO getMyInfo(Long userId);
    List<GetMyPostResponseDTO> getMyPosts(Long userId);
    List<GetMyCommentResponseDTO> getMyComments(Long userId);
}
