package com.example.registration.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "housing_id")
    private Housing housing;

    @Column(name = "data", nullable = false, columnDefinition = "mediumblob")
    private byte[] data;

    public Image(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }
}

