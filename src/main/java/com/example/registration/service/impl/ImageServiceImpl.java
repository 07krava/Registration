package com.example.registration.service.impl;

import com.example.registration.dto.ImageDTO;
import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import com.example.registration.repository.ImageRepository;
import com.example.registration.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDTO saveImage(MultipartFile file, Housing housingEntity) throws IOException {
        Image imageEntity = new Image();
        imageEntity.setFileName(file.getOriginalFilename());
        imageEntity.setHousing(housingEntity);
        imageEntity.setData(file.getBytes());

        Image savedImageEntity = imageRepository.save(imageEntity);

        return convertToDTO(savedImageEntity);
    }
    @Override
    public List<ImageDTO> saveImages(MultipartFile[] files, Housing housingEntity) throws IOException {
        List<ImageDTO> imageDTOS = new ArrayList<>();

        for (MultipartFile file : files) {
            imageDTOS.add(saveImage(file, housingEntity));
        }

        return imageDTOS;
    }

    public ImageDTO getImageById(Long id) {
        Image imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found with id " + id));

        return convertToDTO(imageEntity);
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

    public void deleteImagesByHousingId(Long housingId) {
        imageRepository.deleteByHousingId(housingId);
    }

    private ImageDTO convertToDTO(Image imageEntity) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(imageEntity.getId());
        imageDTO.setFileName(imageEntity.getFileName());
        imageDTO.setData(imageEntity.getData());
        return imageDTO;
    }
}