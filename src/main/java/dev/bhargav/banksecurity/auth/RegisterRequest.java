package dev.bhargav.banksecurity.auth;

import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.Investment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String username;
    private String password;
    private String address;
    private Long number;
    private String identityProof;
    private String role;
    private List<Account> accountList = new ArrayList<>();
    private List<Investment> investmentList = new ArrayList<>();
}
