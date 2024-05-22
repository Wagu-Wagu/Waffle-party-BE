package com.wagu.wafl.api.domain.user.repository;

import com.wagu.wafl.api.domain.user.entity.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, String> {
    AuthProvider searchAuthProviderById(String id);
}
