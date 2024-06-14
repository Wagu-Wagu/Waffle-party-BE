package com.wagu.wafl.api.domain.user.repository;

import com.wagu.wafl.api.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);

    @Query("select u from User u "
            + "join fetch u.comments c "
            + "where u.id = :userId and c.isActive=true "
            + " order by c.createdAt desc")
    Optional<User> findUserAndCommentOrderByCreatedAt(@Param("userId") Long userId);

    @Query("select u from User u "
            + "join fetch u.posts c "
            + "where u.id = :userId"
            + " order by c.createdAt desc")
    Optional<User> findUserAndPostOrderByCreatedAt(@Param("userId") Long userId);
}
