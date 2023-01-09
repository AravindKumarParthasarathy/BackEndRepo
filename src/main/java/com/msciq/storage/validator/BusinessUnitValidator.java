package com.msciq.storage.validator;

import com.msciq.storage.exception.StringLengthExceedsTheLimitException;
import com.msciq.storage.exception.ValueIsNotAlphaNumericException;
import com.msciq.storage.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitValidator {
    public void checkIfGroupCompanyNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.GROUP_COMPANY_CODE, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.GROUP_COMPANY_CODE, name, Constants.FIFTEEN);
        }
    }

    public void checkIfBusinessUnitCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.BUSINESS_UNIT_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.BUSINESS_UNIT_CODE, code, Constants.FIFTEEN);
        }
    }

    public void checkIfBusinessUnitNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.HUNDRED)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.BUSINESS_UNIT_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.BUSINESS_UNIT_NAME, name, Constants.HUNDRED);
        }
    }
}
