package com.wagu.wafl.api.domain.user.repository;

import com.wagu.wafl.api.domain.user.entity.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, String> {
    Optional<AuthProvider> searchAuthProviderById(String id);
}
