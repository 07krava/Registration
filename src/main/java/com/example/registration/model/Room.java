package com.example.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="number")
    private int number;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "title")
    private String title;
}
