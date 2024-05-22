package com.crio.starter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMemeException extends RuntimeException{

    public InvalidMemeException(String msg) {
        super(msg);
    }
    
}
