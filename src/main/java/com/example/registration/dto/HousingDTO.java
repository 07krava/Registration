package com.example.registration.dto;

import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class HousingDTO {
   private Long id;
   private BigDecimal price;
   private String description;
   private String location;
   private String title;
   private Integer amount;
   private List<ImageDTO> images;

   public static HousingDTO convertToDTO(Housing housing) {
      HousingDTO housingDTO = new HousingDTO();
      housingDTO.setId(housing.getId());
      housingDTO.setTitle(housing.getTitle());
      housingDTO.setAmount(housing.getAmount());
      housingDTO.setLocation(housing.getLocation());
      housingDTO.setPrice(housing.getPrice());
      housingDTO.setDescription(housing.getDescription());

      List<ImageDTO> imageDTOS = new ArrayList<>();
      for (Image image : housing.getImages()) {
         ImageDTO photoDTO = new ImageDTO();
         photoDTO.setId(image.getId());
         photoDTO.setFileName(image.getFileName());
         photoDTO.setData(image.getData());
         imageDTOS.add(photoDTO);
      }

      housingDTO.setImages(imageDTOS);
      return housingDTO;
   }
}
