package com.wagu.wafl.api.domain.comment.repository;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("select c from Comment c " +
//            "join fetch c.user u " +
//            "join fetch c.post p " +
//            "where p.id = :postId " +
//            "group by c.parentComment c. " +
//           )
//    List<Comment> findAllCommentByPostId(@Param("postId") Long postId); //todo DTO로 한번에 가져올지 고민

    @Query("select c from Comment c " +
            "where c.post.id = :postId and c.parentComment.id is null " +
            "order by c.createdAt asc"
    )
    List<Comment> findAllCommentByPostId(@Param("postId") Long postId);

//    select * from comment c
//    where c.post_id = 1 and c.parent_comment_id is null
//    group by c.comment_id
//    order by created_at asc;
}
