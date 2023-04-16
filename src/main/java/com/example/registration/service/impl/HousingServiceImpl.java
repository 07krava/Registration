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
import java.util.stream.Collectors;

import static com.example.registration.dto.HousingDTO.convertToDTO;
import static com.example.registration.dto.ImageDTO.convertToPhoto;

@Service
public class HousingServiceImpl implements HousingService {

    private final HousingRepository housingRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Autowired
    public HousingServiceImpl(HousingRepository housingRepository, ImageRepository imageRepository, ImageService imageService) {
        this.housingRepository = housingRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    //worked
    @Override
    public HousingDTO createHousing(HousingDTO housingDTO, MultipartFile[] files) throws IOException {
        Housing housingEntity = new Housing();
        housingEntity.setDescription(housingDTO.getDescription());
        housingEntity.setLocation(housingDTO.getLocation());
        housingEntity.setTitle(housingDTO.getTitle());
        housingEntity.setAmount(housingDTO.getAmount());
        housingEntity.setPrice(housingDTO.getPrice());

        List<Image> imageEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setFileName(file.getOriginalFilename());
            imageDTO.setData(file.getBytes());
            imageDTO.setHousing(housingEntity);
            imageEntities.add(convertToPhoto(imageDTO));

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
                for (Image photoDTO1 : imageList) {
                    photoDTO1.setId(photoDTO1.getId());
                    photoDTO1.setFileName(file.getOriginalFilename());
                    photoDTO1.setData(file.getBytes());
                    photoDTO1.setHousing(housingEntity);
                    imageEntities.add(photoDTO1);
                }
            }
            housingEntity.setImages(imageEntities);
        }
        Housing savedHousing = housingRepository.save(housingEntity);

        return convertToDTO(savedHousing);
    }

    @Override
    public List<ImageDTO> getImagesByHousingId(Long housingId) {
            List<ImageDTO> imageDTOS = new ArrayList<>();
            Optional<Housing> housingOptional = housingRepository.findById(housingId);
            if (housingOptional.isPresent()) {
                Housing housing = housingOptional.get();
                List<Image> photos = housing.getImages();
                if (photos != null) {
                    imageDTOS = photos.stream()
                            .map(photo -> ImageDTO.builder()
                                    .id(photo.getId())
                                    .fileName(photo.getFileName())
                                    .data(photo.getData())
                                    .build())
                            .collect(Collectors.toList());
                }
            }
            return imageDTOS;
        }

    @Override
    public Image getImageByIdFromHousingId(Long housingId, Long imageId) {
        Housing housing = housingRepository.findById(housingId).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + housingId));
        Image image = null;
        for (Image p : housing.getImages()) {
            if (p.getId().equals(imageId)) {
                image = p;
                break;
            }
        }
        if (image == null) {
            throw new EntityNotFoundException();
        }
        return image;
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
    public void deleteHousingById(Long id) {
        Optional<Housing> housingEntityOptional = housingRepository.findById(id);

        if (housingEntityOptional.isPresent()) {
            Housing housingEntity = housingEntityOptional.get();
            housingRepository.delete(housingEntity);
            System.out.println("Housing delete successfully");
        } else {
            throw new EntityNotFoundException("Housing not found with id: " + id);
        }
    }

    @Override
    public void deleteImageByIdFromHousingId(Long housingId, Long imageId) {
        Housing housing = housingRepository.findById(housingId).orElseThrow(EntityNotFoundException::new);
        Image image = housing.getImages().stream()
                .filter(p -> p.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id " + imageId));
        housing.getImages().remove(image);
        imageRepository.deleteById(imageId);
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
