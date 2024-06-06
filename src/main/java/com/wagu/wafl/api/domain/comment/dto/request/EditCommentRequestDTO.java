package com.wagu.wafl.api.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditCommentRequestDTO(
        @NotNull(message = "게시글 아이디는 null일 수 없습니다.")
        Long postId,

        @NotNull(message = "댓글을 생성한 usrId는 null일 수 없습니다.")
        Long commentUserId,

        @NotNull(message = "댓글 id는 null일 수 없습니다.")
        Long commentId,

        @NotNull(message = "비밀 여부는 null일 수 없습니다.")
        boolean isSecret,

        @NotBlank(message = "댓글 내용은 빈 string일 수 없습니다.")
        @Size(min = 1, message = "댓글은 최소 1글자 이상입니다.")
        String content
) {
}
