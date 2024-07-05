package com.wagu.wafl.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WaguException extends RuntimeException{
    private final HttpStatus statusCode;

    public WaguException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
