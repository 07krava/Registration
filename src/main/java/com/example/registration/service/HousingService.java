package com.example.registration.service;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HousingService {
    HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException;

    HousingDTO updateHousing(Long id, HousingDTO housingDTO, MultipartFile[] files) throws IOException;

    List<ImageDTO> getImagesByHousingId(Long housingId);

    List<HousingDTO> getAllHousing();

    HousingDTO getHousingById(Long id);

    void deleteHousing(Long id);
}
