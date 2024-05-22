package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class PostException extends WaguException{
    public PostException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }
}
