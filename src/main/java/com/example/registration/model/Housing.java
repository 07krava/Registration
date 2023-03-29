package com.example.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "housing")
public class Housing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="number")
    private int number;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "amount")
    private int amount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "housing")
    private List<Image> images = new ArrayList<>();
//    private Long previewImageId;

//    public void addImageToHousing(Image image){
//        image.setHousing(this);
//        images.add(image);
//    }

}
