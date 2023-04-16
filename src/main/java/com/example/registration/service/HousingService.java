package com.example.registration.service;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HousingService {

    List<Housing> findByCity(String city);

    HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException;

    HousingDTO updateHousing(Long id, HousingDTO housingDTO, MultipartFile[] files) throws IOException;

    List<ImageDTO> getImagesByHousingId(Long housingId);

    Image getImageById(Long housingId, Long photoId);

    List<HousingDTO> getAllHousing();

    HousingDTO getHousingById(Long id);

    void deleteHousing(Long id);

    void deleteImageByIdFromHousingId(Long housingId, Long imageId);
}
