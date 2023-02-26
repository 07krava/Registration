package com.example.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String phone;
    private String email;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Order> orders;

}
