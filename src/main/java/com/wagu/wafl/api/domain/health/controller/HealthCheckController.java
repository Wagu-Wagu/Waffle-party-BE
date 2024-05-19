package com.wagu.wafl.api.domain.health.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "HealthCheck", description = "Health check 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final Environment env;

    @Operation(
            summary = "HealthCheck 확인",
            description = "dev 서버가 살아 있는지 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서버 응답 가능",
                    content = {@Content(schema = @Schema(implementation = String.class))})
    })
    @GetMapping("api/v1/health")
    public String test() {
        return "hello waffle dev server";
    }
}