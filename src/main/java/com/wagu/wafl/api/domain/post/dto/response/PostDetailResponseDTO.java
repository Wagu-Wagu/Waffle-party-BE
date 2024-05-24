package com.wagu.wafl.api.domain.post.dto.response;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostDetailResponseDTO(PostDetailVO postDetail, List<CommentVO> comments) {
    public static PostDetailResponseDTO of(Post post, boolean isMyPost, List<CommentVO> comments) {
        return PostDetailResponseDTO.builder()
                .postDetail(PostDetailVO.of(post, isMyPost))
                .comments(comments)
                .build();
    }
}

@Builder
record PostDetailVO(String writerNickName, String userImage, OttTag ottTag,
                    boolean isMyPost, LocalDateTime createdAt, String title,
                    String content, Long commentCount) {
    public static PostDetailVO of(Post post, boolean isMyPost) {
        return PostDetailVO.builder()
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


@Builder
record CommentVO(String commenterNickName,  String userImage,
                 LocalDateTime createdAt, String content,
                 boolean isSecret, boolean isParentComment,
                 boolean isMyComment) {
    public static CommentVO of(Comment comment, boolean isMyComment) {
        return CommentVO.builder()
                .commenterNickName(comment.getUser().getNickName())
                .userImage(comment.getUser().getUserImage())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .isSecret(comment.getIsSecret())
                .isParentComment(comment.getParentComment() == null)
                .isMyComment(isMyComment)
                .build();
    }
}
