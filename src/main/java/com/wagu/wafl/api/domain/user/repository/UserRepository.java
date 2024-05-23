package com.wagu.wafl.api.domain.user.repository;

import com.wagu.wafl.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
