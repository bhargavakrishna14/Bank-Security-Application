package dev.bhargav.banksecurity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String roleName;
}
