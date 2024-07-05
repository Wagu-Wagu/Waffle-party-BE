package com.wagu.wafl.api.config.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
}
