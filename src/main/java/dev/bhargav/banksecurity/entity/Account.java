package dev.bhargav.banksecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
public class Account {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(EnumType.STRING)
   private AccountType accountType;

   @Enumerated(EnumType.STRING)
   private AccountStatus status;

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
   @JsonIgnoreProperties("accountList")
   @JoinColumn(name = "user_id")
   private User user;
}
