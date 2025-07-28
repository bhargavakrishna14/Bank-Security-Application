package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(Long userId) {
        super("USER_NOT_FOUND", "No user found with ID: " + userId, HttpStatus.NOT_FOUND);
    }
}
