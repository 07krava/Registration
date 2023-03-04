package com.example.registration.dto;

import com.example.registration.model.Room;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto {

   private String title;
   private int number;
   private BigDecimal price;

   public static RoomDto setRoom(Room room) {
      return RoomDto.builder()
              .number(room.getNumber())
              .price(room.getPrice())
              .title(room.getTitle())
              .build();
   }
}
