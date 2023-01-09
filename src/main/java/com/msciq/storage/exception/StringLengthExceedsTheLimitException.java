package com.msciq.storage.exception;

public class StringLengthExceedsTheLimitException extends RuntimeException {
    public StringLengthExceedsTheLimitException(String fieldName, String fieldValue, int limit) {
        super(String.format("%s `%s` length is greater than %d", fieldName, fieldValue, limit));
    }
}
