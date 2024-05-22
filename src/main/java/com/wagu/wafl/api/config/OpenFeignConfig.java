package com.wagu.wafl.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.wagu.wafl.api.domain.user.kakao")
public class OpenFeignConfig {
}
