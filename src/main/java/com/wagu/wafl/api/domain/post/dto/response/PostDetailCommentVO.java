package com.wagu.wafl.api.domain.post.dto.response;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDetailCommentVO(Long commentId,  Long commentUserId,
                                  String commenterNickName, String userImage,
                                  LocalDateTime createdAt, String content,
                                  boolean isParentComment, boolean isMyComment,
                                  boolean isSecret, boolean isVisible) {

    public static PostDetailCommentVO noAccessSecretComment(Comment comment) {
        return PostDetailCommentVO.builder()
                .commentId(comment.getId())
                .commenterNickName(comment.getUser().getNickName())
                .userImage(comment.getUser().getUserImage())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .commentUserId(comment.getUser().getId())
                .isSecret(comment.getIsSecret())
                .isVisible(false)
                .isParentComment(comment.getParentComment() == null)
                .isMyComment(false)
                .build();
    }


    public static PostDetailCommentVO fullAccessSecretComment(Comment comment, boolean isMyComment) {
        return PostDetailCommentVO.builder()
                .commentId(comment.getId())
                .commenterNickName(comment.getUser().getNickName())
                .userImage(comment.getUser().getUserImage())
                .createdAt(comment.getCreatedAt())
                .commentUserId(comment.getUser().getId())
                .content(comment.getContent())
                .isSecret(comment.getIsSecret())
                .isVisible(true)
                .isParentComment(comment.getParentComment() == null)
                .isMyComment(isMyComment)
                .build();
    }

    public static PostDetailCommentVO isVisibleSecretComment(Comment comment, boolean isMyComment, boolean isVisible) {
        return PostDetailCommentVO.builder()
                .commentId(comment.getId())
                .commenterNickName(comment.getUser().getNickName())
                .userImage(comment.getUser().getUserImage())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .commentUserId(comment.getUser().getId())
                .isSecret(comment.getIsSecret())
                .isVisible(isVisible)
                .isParentComment(comment.getParentComment() == null)
                .isMyComment(isMyComment)
                .build();
    }
}
 /*
         비밀 댓글인 경우
         글 작성자
         비밀 댓글 작성자 본인
         admin user

         비밀 답댓글인 경우
         글 작성자
         비밀 답댓글 작성자 본인
         해당 답댓글이 달린 댓글 작성자
         admin user

         비밀 댓글일 경우 ,
         if (유저 ID가 없을 경우) {
         모든 댓글이 안보임
         } // 처리완

         글 작성자 , admin유저,
         if ( userId == post.getUser().getId() || user.Role==ADMIN ) {
            모든 비밀 댓글 isVisible == true
         } // 처리 완

         글 작성자는 아니지만, 비밀 댓글을 쓴 사람일 경우
         if( userId != post.getUser().getId() && commnet.getParentCommnet()==null && comment.getUser().getId() == userId ) {
            댓글, 답댓글 모두 isVisible == true
         } 처리 완

         글 작성자는 아니지만, 비밀 답댓글을 쓴 사람 일 경우
        if(userId!=post.getUser().getId() && commnet.getParentCommnet()!=null && commnet.getUser().getId() == userId) {
           답댓글에 해당하는 거만 isVisible == true

         */