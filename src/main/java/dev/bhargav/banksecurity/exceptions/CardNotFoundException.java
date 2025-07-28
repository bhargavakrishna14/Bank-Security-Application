package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends BusinessException {
    public CardNotFoundException(Long cardNumber) {
        super("CARD_NOT_FOUND", "No card found with number: " + cardNumber, HttpStatus.NOT_FOUND);
    }
}
