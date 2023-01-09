package com.msciq.storage.validator;

import org.springframework.stereotype.Service;

@Service
public class Validator {

    private static String ALPHA_NUMERIC_REGEX_PATTERN = "^[a-zA-Z0-9_\\s]*$";
    private static String STRING_PATTERN = "^[a-zA-Z]*$";
    public static boolean isEmptyString(String value) {
        return value.isEmpty();
    }

    public static boolean checkIfInputStringLengthIsValid(String value, int length) {
        return value.length() <= length;
    }

    public static boolean checkIfTheInputIsAlphaNumeric(String value) {
         return value.matches(ALPHA_NUMERIC_REGEX_PATTERN);
    }

    public static boolean checkIfTheInputIsString(String value) {
        return value.matches(STRING_PATTERN);
    }
}
