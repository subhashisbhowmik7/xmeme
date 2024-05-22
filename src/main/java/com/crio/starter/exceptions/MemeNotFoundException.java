package com.crio.starter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemeNotFoundException extends RuntimeException {

    public MemeNotFoundException(String msg)
    {
        super(msg);
    }
    
}
