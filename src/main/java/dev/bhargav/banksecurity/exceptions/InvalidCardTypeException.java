package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCardTypeException extends BusinessException {
    public InvalidCardTypeException(String type) {
        super("INVALID_CARD_TYPE", "Invalid card type: " + type, HttpStatus.BAD_REQUEST);
    }
}
