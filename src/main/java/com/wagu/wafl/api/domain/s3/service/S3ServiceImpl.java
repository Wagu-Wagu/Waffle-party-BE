package com.wagu.wafl.api.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wagu.wafl.api.common.exception.AwsException;
import com.wagu.wafl.api.common.exception.PostException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.config.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    private final S3Config s3Config;


    @Override
    public String uploadImages(List<MultipartFile> multipartFiles, String folder) {
        List<String> s3Urls = new ArrayList<>();
        if (!multipartFiles.isEmpty()) {
            multipartFiles.forEach(multipartFile -> {
                String fileName = createFileName(multipartFile.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(multipartFile.getSize());
                objectMetadata.setContentType(multipartFile.getContentType());

                String bucket = s3Config.getBucket();
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    amazonS3.putObject(new PutObjectRequest(bucket + "/" + folder + "/image", fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    s3Urls.add(amazonS3.getUrl(bucket + "/" + folder + "/image", fileName).toString());
                } catch (IOException e) {
                    throw new AwsException(ExceptionMessage.NOT_FOUND_IMAGE_TO_UPLOAD.getMessage(), HttpStatus.NOT_FOUND);
                }
            });
        } else {
            throw new PostException(ExceptionMessage.NOT_REQUESTED_FILE.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return s3Urls.stream()
                .map(s3Url -> '"'+s3Url+'"')
                .collect(Collectors.joining(",","[","]"));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        if (fileName.isEmpty()) {
            throw new AwsException(ExceptionMessage.NOT_FOUND_FILE.getMessage(), HttpStatus.NOT_FOUND);
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new AwsException(ExceptionMessage.NOT_FOUND_FILE.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
