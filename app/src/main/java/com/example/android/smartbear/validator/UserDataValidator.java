package com.example.android.smartbear.validator;

import com.example.android.smartbear.validator.exception.NotValidDataException;
import com.example.android.smartbear.validator.exception.TooLongTextException;
import com.example.android.smartbear.validator.exception.TooShortTextException;

/**
 * Created by parsh on 20.10.2017.
 */

public class UserDataValidator {
    public static void validateName(String name) throws TooShortTextException {
        if (name.isEmpty() || name.length() < 3) {
            throw new TooShortTextException();
        }
    }

    public static void validateAddress(String address) throws TooShortTextException {
        if (address.isEmpty()) {
            throw new TooShortTextException();
        }
    }

    public static void validateEmail(String email) throws TooShortTextException {
        if (email.isEmpty()) {
            throw new TooShortTextException();
        }
    }

    public static void validateMobile(String mobile) throws TooShortTextException, NotValidDataException {
        if (mobile.isEmpty() ) {
            throw new TooShortTextException();
        } else if (mobile.length() != 10) {
            throw new NotValidDataException();
        }
    }

    public static void validatePassword(String password) throws TooShortTextException, TooLongTextException {
        if (password.isEmpty() || password.length() < 4) {
            throw new TooShortTextException();
        } else if (password.length() > 20) {
            throw new TooLongTextException();
        }
    }

    public static void validateReEnterPassword(String reEnterPassword, String password) throws NotValidDataException, TooShortTextException, TooLongTextException {
        if (!password.equals(reEnterPassword)) {
            throw new NotValidDataException();
        } else {
            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4) {
                throw new TooShortTextException();
            } else if (reEnterPassword.length() > 20) {
                throw new TooLongTextException();
            }
        }
    }
}
