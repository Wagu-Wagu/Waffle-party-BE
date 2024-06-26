package com.wagu.wafl.api.domain.post.dto.response;

import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.util.PhotoesParsingToList;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public record OttPostsListResponseDTO(
        PostVO postVO
) {
    public static OttPostsListResponseDTO of(Post post, String nickName) {
        return OttPostsListResponseDTO.builder()
                .postVO(PostVO.of(post, nickName))
                .build();
    }
}

@Builder
record PostVO(
        Long postId,
        OttTag ottTag,
        String title,
        String content,
        List<String> photoes,
        String nickName,
        LocalDateTime createdAt,
        Long commentCount,
        String thumbNail
) {
    public static PostVO of(Post post, String nickName) {
        List<String> photoes = new ArrayList<>();

        if(!Objects.isNull(post.getPhotoes()) &&!post.getPhotoes().isBlank()) {
            photoes = PhotoesParsingToList.toList(post.getPhotoes());
        }
        return PostVO.builder()
                .postId(post.getId())
                .ottTag(post.getOttTag())
                .title(post.getTitle())
                .content(post.getContent())
                .photoes(photoes)
                .nickName(nickName)
                .createdAt(post.getCreatedAt())
                .commentCount(post.getCommentCount())
                .thumbNail(post.getThumbNail())
                .build();
    }
}