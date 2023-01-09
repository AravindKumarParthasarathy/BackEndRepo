package com.msciq.storage.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class ValidatorTest {

    @Test
    public void shouldReturnTrueIfTheInputStringIsEmpty() {
        String input = "";

        assertTrue(Validator.isEmptyString(input));
    }

    @Test
    public void shouldReturnFalseIfTheInputStringIsNotEmpty() {
        String input = "I am not empty";

        assertFalse(Validator.isEmptyString(input));
    }

    @Test
    public void shouldReturnTrueIfTheInputStringSatisfiesTheGivenLength() {
        String input = "group company";

        assertTrue(Validator.checkIfInputStringLengthIsValid(input, 15));
    }

    @Test
    public void shouldReturnFalseIfTheInputStringDoesNotSatisfiesTheGivenLength() {
        String input = "group company code";

        assertFalse(Validator.checkIfInputStringLengthIsValid(input, 15));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab_c123", "abc123", "123 abc asc", "a1c2", "ABC1", "ABC", "123"})
    public void shouldReturnTrueIfTheInputStringIsAlphaNumeric(String input) {
        assertTrue(Validator.checkIfTheInputIsAlphaNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"$123abc", "*a1c2", "%ABC1", "-ABC", "@a123"})
    public void shouldReturnFalseIfTheInputStringIsAlphaNumeric(String input) {
        assertFalse(Validator.checkIfTheInputIsAlphaNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "ABC", "Abc", "aBC"})
    public void shouldReturnTrueIfTheInputIsString(String input) {
        assertTrue(Validator.checkIfTheInputIsString(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab_c123", "$123abc", "a1", "123"})
    public void shouldReturnFalseIfTheInputIsNotAString(String input) {
        assertFalse(Validator.checkIfTheInputIsString(input));
    }

}