package com.example.registration.dto;


import com.example.registration.model.Housing;
import com.example.registration.model.Image;
import com.example.registration.model.Location;
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
    private Location location;
    private String title;
    private boolean isActive;
    private Integer amountPeople;
    private List<ImageDTO> images;

    public static HousingDTO convertToDTO(Housing housing){
        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(housing.getId());
        housingDTO.setTitle(housing.getTitle());
        housingDTO.setAmountPeople(housing.getAmountPeople());
        housingDTO.setLocation(housing.getLocation());
        housingDTO.setPrice(housing.getPrice());
        housingDTO.setDescription(housing.getDescription());
        housingDTO.setActive(housing.isActive());

        LocationDTO location = new LocationDTO();
        location.setCountry(housing.getLocation().getCountry());
        location.setRegion(housing.getLocation().getRegion());
        location.setCity(housing.getLocation().getCity());
        location.setStreet(housing.getLocation().getStreet());
        location.setHouseNumber(housing.getLocation().getHouseNumber());
        location.setApartmentNumber(housing.getLocation().getApartmentNumber());
        location.setZipCode(housing.getLocation().getZipCode());

        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (Image image : housing.getImages()) {
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setFileName(image.getFileName());
            imageDTO.setData(image.getData());
            imageDTOS.add(imageDTO);
        }

        housingDTO.setImages(imageDTOS);
        return housingDTO;
    }

    public static Housing convertToEntity(HousingDTO housingDTO) {
        Housing housing = new Housing();
        housing.setId(housingDTO.getId());
        housing.setTitle(housingDTO.getTitle());
        housing.setAmountPeople(housingDTO.getAmountPeople());
        housing.setPrice(housingDTO.getPrice());
        housing.setDescription(housingDTO.getDescription());
        housing.setActive(housingDTO.isActive());

        Location location = new Location();
        location.setCountry(housingDTO.getLocation().getCountry());
        location.setRegion(housingDTO.getLocation().getRegion());
        location.setCity(housingDTO.getLocation().getCity());
        location.setStreet(housingDTO.getLocation().getStreet());
        location.setHouseNumber(housingDTO.getLocation().getHouseNumber());
        location.setApartmentNumber(housingDTO.getLocation().getApartmentNumber());
        location.setZipCode(housingDTO.getLocation().getZipCode());

        housing.setLocation(location);

        List<Image> imageEntities = new ArrayList<>();
        for (ImageDTO imageDTO : housingDTO.getImages()) {
            Image image = new Image();
            image.setId(imageDTO.getId());
            image.setFileName(imageDTO.getFileName());
            image.setData(imageDTO.getData());
            image.setHousing(housing);
            imageEntities.add(image);
        }
        housing.setImages(imageEntities);

        return housing;
    }
}
