package com.example.registration.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RoomDto {
   private Long id;
   private String title;
   private int number;
   private BigDecimal price;


}
