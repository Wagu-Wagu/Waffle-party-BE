package com.wagu.wafl.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WaguConfig {
    @Value("${app.version}")
    private String waguVersion;
}
