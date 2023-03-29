package com.example.registration.dto;

import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private String fileName;
    private Housing housing;
    private byte[] data;

    public static ImageDTO convertToDTO(Image imageEntity) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(imageEntity.getId());
        imageDTO.setFileName(imageEntity.getFileName());
        imageDTO.setHousing(imageEntity.getHousing());
        imageDTO.setData(imageEntity.getData());
        return imageDTO;
    }

    public static Image convertToPhoto(ImageDTO imageDTO){
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setFileName(imageDTO.getFileName());
        image.setHousing(imageDTO.getHousing());
        image.setData(imageDTO.getData());
        return image;
    }
}
