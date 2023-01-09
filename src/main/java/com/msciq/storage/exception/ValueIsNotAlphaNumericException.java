package com.msciq.storage.exception;

public class ValueIsNotAlphaNumericException extends RuntimeException {
    public ValueIsNotAlphaNumericException(String fieldName, String fieldValue) {
        super(String.format("%s `%s` is not Alphanumeric", fieldName, fieldValue));
    }
}
