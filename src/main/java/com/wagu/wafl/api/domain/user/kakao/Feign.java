package com.wagu.wafl.api.domain.user.kakao;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Feign", url="")
public interface Feign {
}
