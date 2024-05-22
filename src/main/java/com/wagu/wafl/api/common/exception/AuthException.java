package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends WaguException{
    public AuthException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }
}
