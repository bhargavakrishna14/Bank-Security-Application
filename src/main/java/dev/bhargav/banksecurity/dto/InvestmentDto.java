package dev.bhargav.banksecurity.dto;

import lombok.Data;

@Data
public class InvestmentDto {

    private String investmentType;

    private double amount;

    private String duration;
}
