package com.wagu.wafl.api.domain.comment.dto.request;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentReplyDTO(
        @NotNull(message = "게시글 아이디는 null일 수 없습니다.")
        Long postId,

        @NotNull(message = "비밀 여부는 null일 수 없습니다.")
        boolean isSecret,

        @NotNull(message = "댓글 아이디는 null일 수 없습니다.")
        Long parentCommentId,

        @NotBlank(message = "댓글 내용은 빈 string일 수 없습니다.")
        @Size(min = 1, message = "댓글은 최소 1글자 이상입니다.")
        String content
) {
    public static Comment toEntity(User user, Post post, Comment parentComment, String content, boolean isSecret) {
            return Comment.builder()
                    .user(user)
                    .post(post)
                    .parentComment(parentComment)
                    .content(content)
                    .isSecret(isSecret)
                    .build();
    }
}
