package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(Long accountNumber) {
        super("ACCOUNT_NOT_FOUND", "No account found with number: " + accountNumber, HttpStatus.NOT_FOUND);
    }
}
