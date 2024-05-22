package com.wagu.wafl.api.domain.post.controller;

import com.wagu.wafl.api.common.ApiResponse;
import com.wagu.wafl.api.domain.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final S3Service s3Service;

    @PostMapping("/s3test")
    public ResponseEntity<ApiResponse> s3TestController(
            @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        val response = s3Service.uploadImages(multipartFiles, "photos");
        return ResponseEntity.ok(ApiResponse.success("ok", response));
    }
}
