package com.gestionale.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<CustomExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setHttpStatus(status);
        exceptionResponse.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, status);
    }
    @ExceptionHandler(value = {NotAvailableException.class})
    public ResponseEntity<CustomExceptionResponse> handleEntityNotAvailableException(NotAvailableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setHttpStatus(status);
        exceptionResponse.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
