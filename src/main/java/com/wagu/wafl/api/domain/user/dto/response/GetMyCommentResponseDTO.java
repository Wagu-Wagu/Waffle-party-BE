package com.wagu.wafl.api.domain.user.dto.response;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record GetMyCommentResponseDTO(
        Long postId,
        String postTitle,
        String commentContent,
        OttTag ottTag,
        LocalDate createdAt
) {
    public static GetMyCommentResponseDTO of(Post post, Comment comment) {
        return GetMyCommentResponseDTO.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .commentContent(comment.getContent())
                .ottTag(post.getOttTag())
                .createdAt(LocalDate.from(comment.getCreatedAt()))
                .build();
    }
}
