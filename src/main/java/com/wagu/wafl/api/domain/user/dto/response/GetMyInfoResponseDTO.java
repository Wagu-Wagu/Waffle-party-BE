package com.wagu.wafl.api.domain.user.dto.response;

import com.wagu.wafl.api.domain.user.entity.User;
import lombok.Builder;

@Builder
public record GetMyInfoResponseDTO(
        UserInfoVO userInfo, String waguVersion
) {
    public static GetMyInfoResponseDTO of(User user, String waguVersion) {
        return GetMyInfoResponseDTO.builder()
                .userInfo(UserInfoVO.of(user))
                .waguVersion(waguVersion)
                .build();
    }
}

@Builder
record UserInfoVO(Long userId, String userImage, String nickName, String email, int postCount, int commentCount) {
    public static UserInfoVO of(User user){
        return UserInfoVO.builder()
                .userId(user.getId())
                .userImage(user.getUserImage())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .postCount(user.getPosts().size())
                .commentCount(user.getComments().size())
                .build();
    }
}
