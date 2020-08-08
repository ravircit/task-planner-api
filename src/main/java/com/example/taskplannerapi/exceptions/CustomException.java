package com.example.taskplannerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomException extends RuntimeException {
    //public static final long serialVersionUID
    public CustomException(String message) {
        super(message);
    }
}
