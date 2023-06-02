package com.example.demo.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConstraintViolationHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleIllegalException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            sb.append("\n" + violation.getMessage());
        }
        return sb.toString();
    }
}
