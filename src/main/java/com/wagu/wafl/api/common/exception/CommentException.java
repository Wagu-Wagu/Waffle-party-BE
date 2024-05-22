package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class CommentException extends WaguException{
    public CommentException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
