package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCardLimitException extends BusinessException {
    public InvalidCardLimitException(Double limit) {
        super("INVALID_CARD_LIMIT", "Daily limit " + limit + " is not allowed for this card type.", HttpStatus.BAD_REQUEST);
    }
}
