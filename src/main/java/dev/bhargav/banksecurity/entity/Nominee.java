package dev.bhargav.banksecurity.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
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
