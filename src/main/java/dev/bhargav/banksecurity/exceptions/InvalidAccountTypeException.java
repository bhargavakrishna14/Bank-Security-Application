package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAccountTypeException extends BusinessException {
    public InvalidAccountTypeException(String type) {
        super("INVALID_ACCOUNT_TYPE", "Invalid account type: " + type, HttpStatus.BAD_REQUEST);

    }
}
