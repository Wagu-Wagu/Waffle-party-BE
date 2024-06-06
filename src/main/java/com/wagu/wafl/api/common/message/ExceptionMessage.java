package com.wagu.wafl.api.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    // auth
    INVALID_KAKAO_TOKEN("유효하지 않은 카카오 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    NOT_PARSED_TO_ID("Id로 변환하지 못했습니다."),

    // s3 upload
    NOT_FOUND_IMAGE_TO_UPLOAD("등록할 이미지를 찾을 수 없습니다."),
    NOT_FOUND_FILE("등록할 파일을 찾을 수 없습니다."),
    NOT_REQUESTED_FILE("요청된 파일이 없습니다."),
    INVALID_MULTIPART_EXTENSION_EXCEPTION("확장자가 올바르지 않습니다."),


    ALREADY_DID_ONBOARD("이미 온보딩을 한 유저입니다."),
    NOT_FOUND_USER("id에 해당하는 유저를 찾을 수 없습니다."),
    ALREADY_EXIST_NICKNAME("이미 존재하는 유저명 입니다."),
    IS_THE_SAME_NICKNAME("바꾸는 닉네임과 현재 닉네임이 일치합니다."),
    EXCEED_MAX_FILE_SIZE("파일 크기 초과 오류, 업로드하는 파일은 2MB를 초과할 수 없습니다."),

    // post
    NOT_FOUND_POST("id에 해당하는 게시글을 찾을 수 없습니다."),
    IS_NOT_POST_OWNER("작성한 게시글의 주인이 아닙니다."),

    // comment
    NOT_FOUND_COMMENT("id에 해당하는 댓글을 찾을 수 없습니다.");

    private final String message;
}
