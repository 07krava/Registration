package com.example.registration.dto;

import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private String fileName;
    private Housing housing;
    private byte[] data;

    public static ImageDTO convertToDTO(Image photoEntity) {
        ImageDTO photoDTO = new ImageDTO();
        photoDTO.setId(photoEntity.getId());
        photoDTO.setFileName(photoEntity.getFileName());
        photoDTO.setHousing(photoEntity.getHousing());
        photoDTO.setData(photoEntity.getData());
        return photoDTO;
    }

    public static Image convertToPhoto(ImageDTO imageDTO) {
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setFileName(imageDTO.getFileName());
        image.setHousing(imageDTO.getHousing());
        image.setData(imageDTO.getData());
        return image;
    }

    public ImageDTO convertMultipartFileToPhotoDTO(MultipartFile file, Long id) {
        ImageDTO photoDTO = new ImageDTO();
        photoDTO.setId(id);
        photoDTO.setFileName(file.getOriginalFilename());
        return photoDTO;
    }
}