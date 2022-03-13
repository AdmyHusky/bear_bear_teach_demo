package com.example.bear_bear_teach_demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CommonException extends RuntimeException {

    protected HttpStatus status;
    protected String code;

    public CommonException(String message) {
        super(message);
    }

}
