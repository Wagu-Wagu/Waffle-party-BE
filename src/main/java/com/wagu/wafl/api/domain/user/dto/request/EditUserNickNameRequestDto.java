package com.wagu.wafl.api.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EditUserNickNameRequestDto (

        @NotNull(message = "유저 닉네임은 null일 수 없습니다.")
        @NotBlank(message = "유저 닉네임은 빈스트링 일 수 없습니다.")
        @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]*$", message = "사용자이름은 자음, 모음, 영어, 한글, 숫자만 가능합니다.")
        @Size(min = 1, max = 8)
       String nickName) {

}
