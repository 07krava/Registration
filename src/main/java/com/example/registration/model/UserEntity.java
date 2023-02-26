package com.example.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String phone;
    private String email;
    private String password;
    // enum must be here

    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany
    //@JoinColumn(name = "orders_id")
    private Order order;

}
