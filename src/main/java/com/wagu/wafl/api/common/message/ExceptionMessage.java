package com.wagu.wafl.api.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    // auth
    INVALID_KAKAO_TOKEN("유효하지 않은 카카오 토큰입니다."),

    // s3 upload
    NOT_FOUND_IMAGE_TO_UPLOAD("등록할 이미지를 찾을 수 없습니다."),
    NOT_FOUND_FILE("등록할 파일을 찾을 수 없습니다."),
    NOT_REQUESTED_FILE("요청된 파일이 없습니다."),
    INVALID_MULTIPART_EXTENSION_EXCEPTION("확장자가 올바르지 않습니다."),


    /* user */
    NOT_FOUND_USER("id에 해당하는 유저를 찾을 수 없습니다."),

    /* post */
    NOT_FOUND_POST("id에 해당하는 게시글을 찾을 수 없습니다.");

    private final String message;
}
