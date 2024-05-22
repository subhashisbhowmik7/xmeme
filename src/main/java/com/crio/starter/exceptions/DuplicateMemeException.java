package com.crio.starter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateMemeException extends RuntimeException {
    public DuplicateMemeException()
    {
        super("Meme already exists with same attributes.");
    }
}
