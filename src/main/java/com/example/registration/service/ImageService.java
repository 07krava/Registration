package com.example.registration.service;

import com.example.registration.dto.ImageDTO;
import com.example.registration.model.Housing;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ImageDTO saveImage(MultipartFile file, Housing housing) throws IOException;

    List<ImageDTO> saveImages(MultipartFile[] files, Housing housing) throws IOException;

    ImageDTO getPhotoById(Long photoId);

    void deletePhotoById(Long id);

    void deletePhotosByHousingId(Long housingId);
}
