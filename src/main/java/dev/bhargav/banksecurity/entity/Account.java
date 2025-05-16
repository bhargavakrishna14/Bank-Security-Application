package dev.bhargav.banksecurity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String status;

    private double balance;

    private float interestRate;

    @Enumerated(EnumType.STRING)
    private BranchType branch;

    private String proof;

    private Date openingDate;

    private Long accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Nominee nominee;

    @OneToOne(cascade = CascadeType.ALL)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
