package dev.bhargav.banksecurity.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardNumber;

    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private double dailyLimit;

    private int cvv;

    private Date allocationDate;

    private Date expiryDate;

    private Long pin;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

}
