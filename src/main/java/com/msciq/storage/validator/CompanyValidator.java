package com.msciq.storage.validator;

import com.msciq.storage.exception.NotAStringException;
import com.msciq.storage.exception.StringLengthExceedsTheLimitException;
import com.msciq.storage.exception.ValueIsNotAlphaNumericException;
import com.msciq.storage.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator {
    public void checkIfGroupCompanyNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.GROUP_COMPANY_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.GROUP_COMPANY_NAME, name, Constants.FIFTEEN);
        }
    }

    public void checkIfCompanyNameIsValid(String name) {
        if (Validator.checkIfInputStringLengthIsValid(name, Constants.HUNDRED)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(name)) {
                throw new ValueIsNotAlphaNumericException(Constants.COMPANY_NAME, name);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.COMPANY_NAME, name, Constants.HUNDRED);
        }
    }

    public void checkIfCompanyCodeIsValid(String code) {
        if (Validator.checkIfInputStringLengthIsValid(code, Constants.FIFTEEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(code)) {
                throw new ValueIsNotAlphaNumericException(Constants.COMPANY_CODE, code);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.COMPANY_CODE, code, Constants.FIFTEEN);
        }
    }

    public void checkIfCompanyCurrencyIsValid(String currency) {
        if (Validator.checkIfTheInputIsString(currency)) {
            if (!Validator.checkIfInputStringLengthIsValid(currency, Constants.THREE)) {
                throw new StringLengthExceedsTheLimitException(Constants.CURRENCY, currency, Constants.THREE);
            }
        } else {
            throw new NotAStringException(Constants.CURRENCY, currency);
        }
    }

    public void checkIfCompanyFiscalYearIsValid(String fiscalYear) {
        if (Validator.checkIfInputStringLengthIsValid(fiscalYear, Constants.TEN)) {
            if (!Validator.checkIfTheInputIsAlphaNumeric(fiscalYear)) {
                throw new ValueIsNotAlphaNumericException(Constants.FISCAL_YEAR, fiscalYear);
            }
        } else {
            throw new StringLengthExceedsTheLimitException(Constants.FISCAL_YEAR, fiscalYear, Constants.TEN);
        }
    }
}
