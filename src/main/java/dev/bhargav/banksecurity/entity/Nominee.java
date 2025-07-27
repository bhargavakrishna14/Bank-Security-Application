package dev.bhargav.banksecurity.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Nominee {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String relation;

   private String name;

   private Long accountNumber;

   private String gender;

   private int age;

}
