package com.msciq.storage.validator;

import com.msciq.storage.exception.StringLengthExceedsTheLimitException;
import com.msciq.storage.exception.ValueIsNotAlphaNumericException;
import com.msciq.storage.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class LocationValidator {

    public void checkIfCompanyCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.COMPANY_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.COMPANY_CODE, code, Constants.FIFTEEN);
        }
    }

    public void checkIfLocationCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.LOCATION_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.LOCATION_CODE, code, Constants.FIFTEEN);
        }
    }

    public void checkIfLocationNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.HUNDRED)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.LOCATION_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.LOCATION_NAME, name, Constants.HUNDRED);
        }
    }

}
