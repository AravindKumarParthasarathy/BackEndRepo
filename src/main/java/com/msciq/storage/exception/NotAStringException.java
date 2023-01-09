package com.msciq.storage.exception;

public class NotAStringException extends RuntimeException {
    public NotAStringException(String fieldName, String value) {
        super(String.format("%s should be a string", fieldName, value));
    }
}
