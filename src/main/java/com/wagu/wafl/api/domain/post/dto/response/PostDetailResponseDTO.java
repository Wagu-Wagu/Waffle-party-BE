package com.wagu.wafl.api.domain.post.dto.response;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.util.PhotoesParsingToList;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostDetailResponseDTO(PostDetailVO postDetail, List<PostDetailCommentVO> comments) {
    public static PostDetailResponseDTO of(Post post, boolean isMyPost, List<PostDetailCommentVO> comments) {
        return PostDetailResponseDTO.builder()
                .postDetail(PostDetailVO.of(post, isMyPost))
                .comments(comments)
                .build();
    }

}

@Builder
record PostDetailVO(Long postId, String writerNickName, String userImage,
                    OttTag ottTag, boolean isMyPost, List<String> photoes,
                    LocalDateTime createdAt,
                    String title, String content, Long commentCount) {

    public static PostDetailVO of(Post post, boolean isMyPost) {
        List<String> photoes = new ArrayList<>();

        if(!Objects.isNull(post.getPhotoes()) &&!post.getPhotoes().isBlank()) {
            photoes = PhotoesParsingToList.toList(post.getPhotoes());
        }
        return PostDetailVO.builder()
                .postId(post.getId())
                .photoes(photoes)
                .writerNickName(post.getUser().getNickName())
                .userImage(post.getUser().getUserImage())
                .ottTag(post.getOttTag())
                .isMyPost(isMyPost)
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(post.getCommentCount())
                .build();
    }
}


