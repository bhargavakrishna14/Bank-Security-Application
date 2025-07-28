package dev.bhargav.banksecurity.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "UserName is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
