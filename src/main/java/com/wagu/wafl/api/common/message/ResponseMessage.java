package com.wagu.wafl.api.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    //social
    SUCCESS_LOGIN("로그인에 성공하였습니다."),

    //user
    SUCCESS_EDIT_USER_NICKNAME("유저 닉네임 변경에 성공했습니다."),

    //post
    SUCCESS_GET_POST_LIST("게시글 목록 조회를 성공했습니다.");

    private final String message;
}
