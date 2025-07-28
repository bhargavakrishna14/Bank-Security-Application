package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class CardAlreadyExistsException extends BusinessException {
    public CardAlreadyExistsException(Long accountNumber) {
        super("CARD_ALREADY_EXISTS", "Account with number " + accountNumber + " already has a card.", HttpStatus.BAD_REQUEST);
    }
}
