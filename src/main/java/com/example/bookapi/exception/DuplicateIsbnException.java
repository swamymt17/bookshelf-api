package com.example.bookapi.exception;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("A book with ISBN " + isbn + " already exists");
    }
}
