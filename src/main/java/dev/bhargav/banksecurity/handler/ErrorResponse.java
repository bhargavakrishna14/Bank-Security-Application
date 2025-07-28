package dev.bhargav.banksecurity.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        List<String> errors
) {

}
