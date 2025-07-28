package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidInvestmentTypeException extends BusinessException {
    public InvalidInvestmentTypeException(String message) {
        super("INVALID_INVESTMENT_TYPE", message, HttpStatus.BAD_REQUEST);
    }
}
