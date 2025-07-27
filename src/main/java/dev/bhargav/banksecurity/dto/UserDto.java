package dev.bhargav.banksecurity.dto;

import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.Investment;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
   private String name;
   private String username;
   private String password;
   private String address;
   private Long number;
   private String identityProof;
   private List<Account> accountList = new ArrayList<>();
   private List<Investment> investmentList = new ArrayList<>();
}
