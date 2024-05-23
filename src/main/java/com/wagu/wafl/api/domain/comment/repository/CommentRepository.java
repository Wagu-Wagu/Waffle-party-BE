package com.wagu.wafl.api.domain.comment.repository;

import com.wagu.wafl.api.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
