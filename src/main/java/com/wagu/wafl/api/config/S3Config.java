package com.wagu.wafl.api.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Config {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.bucket}")
    private String bucket;

    @Value("${cloud.aws.region}")
    private String region;

    @Value("${s3.maxFileSize}")
    private String maxFileSize;


    @Value("${s3.baseURL.user}")
    private String userS3ImageBaseURL;

    @Value("${s3.folder.user}")
    private String userImageFolderName;

    @Value("${s3.baseURL.post}")
    private String postS3ImageBaseURL;

    @Value("${s3.folder.post}")
    private String postImageFolderName;

    @Bean
    public BasicAWSCredentials createAwsCredentials(){
        return new BasicAWSCredentials(accessKey,secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = createAwsCredentials();
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
