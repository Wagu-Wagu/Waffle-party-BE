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
record UserInfoVO(String userImage, String email, int postCount, int commentCount) {
    public static UserInfoVO of(User user){
        return UserInfoVO.builder()
                .userImage(user.getUserImage())
                .email(user.getEmail())
                .postCount(user.getPosts().size())
                .commentCount(user.getComments().size())
                .build();
    }
}
