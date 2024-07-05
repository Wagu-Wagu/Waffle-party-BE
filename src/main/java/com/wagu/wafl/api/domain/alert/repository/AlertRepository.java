package com.wagu.wafl.api.domain.alert.repository;

import com.wagu.wafl.api.domain.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByUserIdAndCommentId(Long userId, Long commentId);
    Optional<Alert> findByUserIdAndPostId(Long userId, Long postId);
}
