package com.example.registration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "housing")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Housing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private Integer amount;

    @OneToMany(mappedBy = "housing", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
}
