package com.example.bear_bear_teach_demo.controller.advice;

import com.example.bear_bear_teach_demo.exception.CommonException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonAdvice extends ResponseEntityExceptionHandler {

    @Getter
    @Setter
    //inner class
    private class ExceptionResponse {
        private String code;
        private String message;
        private LocalDateTime timestamp;
    }

    private ResponseEntity<Object> handle(String message, HttpStatus status, String code) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("service-bear-" + code);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-developer", "Admy");
        return ResponseEntity.status(status).headers(headers).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(ex.getMessage(), status, String.valueOf(status.value()));
    }

    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<?> handleCommonException(CommonException ex) {
        return handle(ex.getMessage(), ex.getStatus(), ex.getCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception ex) {
        return handle(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
