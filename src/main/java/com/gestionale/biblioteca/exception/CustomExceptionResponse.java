package com.gestionale.biblioteca.exception;

import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

public class CustomExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime dateTime;

    public CustomExceptionResponse(LocalDateTime dateTime, HttpStatus httpStatus, String message) {
        this.dateTime = dateTime;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomExceptionResponse() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
