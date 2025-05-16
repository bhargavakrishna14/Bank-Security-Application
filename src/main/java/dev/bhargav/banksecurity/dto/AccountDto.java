package dev.bhargav.banksecurity.dto;

import dev.bhargav.banksecurity.entity.Nominee;
import lombok.Data;

@Data
public class AccountDto {

    private String accountType;

    private double balance;

    private String proof;

    private Nominee nominee;

}