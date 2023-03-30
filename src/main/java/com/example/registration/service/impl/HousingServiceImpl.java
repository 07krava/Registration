package com.example.registration.service.impl;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import com.example.registration.repository.HousingRepository;
import com.example.registration.repository.ImageRepository;
import com.example.registration.service.HousingService;
import com.example.registration.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.registration.dto.HousingDTO.convertToDTO;
import static com.example.registration.dto.ImageDTO.convertToPhoto;

@Service
public class HousingServiceImpl implements HousingService {

    private final HousingRepository housingRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public HousingServiceImpl(HousingRepository housingRepository, ImageRepository imageRepository, ImageService imageService) {
        this.housingRepository = housingRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    //worked
    @Override
    public HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException {
        Housing housingEntity = new Housing();
        housingEntity.setNumber(housingDTO.getNumber());
        housingEntity.setDescription(housingDTO.getDescription());
        housingEntity.setLocation(housingDTO.getLocation());
        housingEntity.setTitle(housingDTO.getTitle());
        housingEntity.setAmount(housingDTO.getAmount());
        housingEntity.setPrice(housingDTO.getPrice());

        List<Image> imageEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            ImageDTO photoDTO = new ImageDTO();
            photoDTO.setFileName(file.getOriginalFilename());
            photoDTO.setData(file.getBytes());
            photoDTO.setHousing(housingEntity);
            imageEntities.add(convertToPhoto(photoDTO));

        }
        housingEntity.setImages(imageEntities);

        Housing savedHousing = housingRepository.save(housingEntity);

        return convertToDTO(savedHousing);
    }

    // worked
    @Override
    public HousingDTO updateHousing(Long housingId, HousingDTO housingDTO, MultipartFile[] files) throws IOException {
        Housing housingEntity = housingRepository.findById(housingId)
                .orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + housingId));

        // Update fields of HousingEntity based on HousingDTO
        housingEntity.setNumber(housingDTO.getNumber());
        housingEntity.setTitle(housingDTO.getTitle());
        housingEntity.setDescription(housingDTO.getDescription());
        housingEntity.setLocation(housingDTO.getLocation());
        housingEntity.setAmount(housingDTO.getAmount());
        housingEntity.setPrice(housingDTO.getPrice());

        // Update photos of HousingEntity based on files
        if (files != null && files.length > 0) {
            List<Image> imageEntities = new ArrayList<>();
            for (MultipartFile file : files) {

                List<Image> imageList = housingEntity.getImages();
                for (Image image : imageList) {
                    image.setId(image.getId());
                    image.setFileName(file.getOriginalFilename());
                    image.setData(file.getBytes());
                    image.setHousing(housingEntity);
                    imageEntities.add(image);
                }
            }
            housingEntity.setImages(imageEntities);
        }
        Housing savedHousing = housingRepository.save(housingEntity);

        return convertToDTO(savedHousing);
    }
    //Don't work
    @Override
    public List<ImageDTO> getImagesByHousingId(Long housingId) {
        Optional<Housing> optionalHousing = housingRepository.findById(housingId);
        if (!optionalHousing.isPresent()) {
            throw new EntityNotFoundException("Housing not found with id: " + housingId);
        }
        Housing housingEntity = optionalHousing.get();

        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (Image imageEntity : housingEntity.getImages()) {
            imageDTOS.add(ImageDTO.convertToDTO(imageEntity));
        }
        return imageDTOS;
    }

    //worked
    @Override
    public List<HousingDTO> getAllHousing() {
        List<Housing> housingEntities = housingRepository.findAll();
        List<HousingDTO> housingDTOS = new ArrayList<>();

        for (Housing housingEntity : housingEntities) {
            housingDTOS.add(convertToDTO(housingEntity));
        }

        return housingDTOS;
    }

    //worked
    @Override
    public void deleteHousing(Long id) {
        Optional<Housing> housingEntityOptional = housingRepository.findById(id);

        if (housingEntityOptional.isPresent()) {
            Housing housingEntity = housingEntityOptional.get();
            housingRepository.delete(housingEntity);
            System.out.println("Housing delete successfully");
        } else {
            throw new EntityNotFoundException("Housing not found with id: " + id);
        }
    }

    //worked
    @Override
    public HousingDTO getHousingById(Long id) {
        Optional<Housing> housingEntityOptional = housingRepository.findById(id);

        if (housingEntityOptional.isPresent()) {
            Housing housingEntity = housingEntityOptional.get();
            return convertToDTO(housingEntity);
        } else {
            throw new EntityNotFoundException("Housing not found with id: " + id);
        }
    }
}
