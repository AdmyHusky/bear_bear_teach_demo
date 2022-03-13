package com.example.bear_bear_teach_demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends CommonException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String code = "404";

    public NotFoundException(String message) {
        super(message);
    }

}
