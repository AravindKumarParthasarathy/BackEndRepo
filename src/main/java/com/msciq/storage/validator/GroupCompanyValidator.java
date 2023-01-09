package com.msciq.storage.validator;

import com.msciq.storage.exception.NotAStringException;
import com.msciq.storage.exception.StringLengthExceedsTheLimitException;
import com.msciq.storage.exception.ValueIsNotAlphaNumericException;
import com.msciq.storage.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class GroupCompanyValidator {

    public void checkIfGroupCompanyCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.GROUP_COMPANY_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.GROUP_COMPANY_CODE, code, Constants.FIFTEEN);
        }
    }

    public void checkIfGroupCompanyNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.HUNDRED)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.GROUP_COMPANY_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.GROUP_COMPANY_NAME, name, Constants.HUNDRED);
        }
    }

    public void checkIfGroupCompanyCurrencyIsValid(String currency) {
        if (Validator.checkIfTheInputIsString(currency)) {
            if (!Validator.checkIfInputStringLengthIsValid(currency, Constants.THREE)) {
                throw new StringLengthExceedsTheLimitException(Constants.CURRENCY, currency, Constants.THREE);
            }
        } else {
            throw new NotAStringException(Constants.CURRENCY, currency);
        }
    }
}
