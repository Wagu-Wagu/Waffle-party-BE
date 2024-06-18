package com.wagu.wafl.api.domain.user.dto.response;

import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetMyPostResponseDTO(
        OttTag ottTag,
        Long postId,
        String postTitle,
        String postContent,
        String authorNickName,
        LocalDateTime createdAt,
        Long commentCount,
        String thumbNail
) {
    public static GetMyPostResponseDTO of(Post post, String authorNickName) {
        return GetMyPostResponseDTO.builder()
                .ottTag(post.getOttTag())
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .authorNickName(authorNickName)
                .createdAt(LocalDateTime.from(post.getCreatedAt()))
                .commentCount(post.getCommentCount())
                .thumbNail(post.getThumbNail())
                .build();
    }
}
