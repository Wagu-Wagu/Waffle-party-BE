package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class AwsException extends WaguException{
    public AwsException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
