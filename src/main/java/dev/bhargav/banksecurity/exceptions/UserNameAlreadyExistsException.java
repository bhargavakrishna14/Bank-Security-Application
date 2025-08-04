package dev.bhargav.banksecurity.exceptions;

import org.springframework.http.HttpStatus;

public class UserNameAlreadyExistsException extends BusinessException {

    public UserNameAlreadyExistsException(String username) {
        super("USER_ALREADY_EXISTS", "Username '" + username + "' already exists.", HttpStatus.BAD_REQUEST);
    }
}
