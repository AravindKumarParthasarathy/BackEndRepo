package com.msciq.storage.validator;

import com.msciq.storage.exception.StringLengthExceedsTheLimitException;
import com.msciq.storage.exception.ValueIsNotAlphaNumericException;
import com.msciq.storage.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class DepartmentValidator {
    public void checkIfDepartmentNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.HUNDRED)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.DEPARTMENT_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.DEPARTMENT_NAME, name, Constants.HUNDRED);
        }
    }

    public void checkIfDepartmentCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.DEPARTMENT_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.DEPARTMENT_CODE, code, Constants.FIFTEEN);
        }
    }
}
