package com.gestionale.biblioteca.exception;

@SuppressWarnings("serial")
public class NotAvailableException extends RuntimeException{
    public NotAvailableException(String message) {
        super(message);
    }
}
