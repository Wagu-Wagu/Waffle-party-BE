package com.wagu.wafl.api.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    //user
    SUCCESS_EDIT_USER_NICKNAME("유저 닉네임 변경에 성공했습니다."),

    //post
    SUCCESS_GET_POST_LIST("게시글 목록 조회를 성공했습니다."),
    SUCCESS_CREATE_POST_COMMENT("게시글 답글 달기에 성공했습니다.");

    private final String message;
}
