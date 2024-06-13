package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class AlertException extends WaguException{
    public AlertException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
