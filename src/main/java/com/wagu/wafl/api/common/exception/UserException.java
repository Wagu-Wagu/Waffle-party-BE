package com.wagu.wafl.api.common.exception;

import org.springframework.http.HttpStatus;

public class UserException extends WaguException{

    public UserException(String message, HttpStatus statusCode) { super(message, statusCode);}
}
