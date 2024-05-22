package com.wagu.wafl.api.domain.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    String uploadImages(List<MultipartFile> multipartFile, String folder);
}
