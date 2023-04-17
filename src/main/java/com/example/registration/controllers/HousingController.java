package com.example.registration.controllers;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import com.example.registration.model.Housing;
import com.example.registration.service.HousingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/housing")
public class HousingController {

    @Autowired
    private HousingService housingService;

    @PostMapping("/owner/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<HousingDTO> createHousing(@ModelAttribute HousingDTO housing, @RequestParam("file") MultipartFile[] files) throws IOException, IOException {
        HousingDTO newHousing = housingService.createHousing(housing, files);
        return new ResponseEntity<>(newHousing, HttpStatus.OK);
    }

    @GetMapping("/search/{city}")
    public List<Housing> searchByCity(@PathVariable("city") String city, @ModelAttribute Housing housing) {
        return housingService.findByCity(city);
    }

    @GetMapping("/allHousing")
    public List<HousingDTO> getAllHousing() {
        return housingService.getAllHousing();
    }

    @PutMapping("/owner/updateHousing/{id}")
    public HousingDTO updateHousing(@PathVariable Long id, @ModelAttribute HousingDTO housingDTO, @RequestParam("file") MultipartFile[] files) throws IOException {
        housingDTO.setId(id);
        return housingService.updateHousing(id, housingDTO, files);
    }

    @DeleteMapping("/owner/deleteHousing/{id}")
    public ResponseEntity<String> deleteHousingById(@PathVariable Long id) {
        housingService.deleteHousing(id);
        return new ResponseEntity<>("Housing "+ id + " delete successfully!", HttpStatus.OK);
    }

    @GetMapping("/getHousing/{id}")
    public HousingDTO getHousingById(@PathVariable Long id) {
        return housingService.getHousingById(id);
    }

    @GetMapping("/getImagesByHousing/{id}")
    public List<ImageDTO> getImageByHousingId(@PathVariable Long id){
        return housingService.getImagesByHousingId(id);
    }

    @GetMapping("/{housingId}/image/{imageId}")
    public ResponseEntity<ImageDTO> getPhotoById(@PathVariable Long housingId, @PathVariable Long imageId) {
        log.info("Here start method getImageById");
        HousingDTO housing = housingService.getHousingById(housingId);
        ImageDTO photo = null;
        if (housing.getImages() != null ) {
            for (ImageDTO p : housing.getImages()) {
                if (p.getId().equals(imageId) && p.getId() != null) {
                    photo = p;
                    break;
                }
            }
        }
        if (photo == null){
            log.info("This message you can see if your image not found");
            throw new EntityNotFoundException("Image not found with this id: " + imageId);
        }
        return ResponseEntity.ok(photo);
    }

    @DeleteMapping("/{housingId}/deleteImage/{imageId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long housingId, @PathVariable Long imageId) {
        housingService.deleteImageByIdFromHousingId(housingId, imageId);
        return ResponseEntity.noContent().build();
    }
}


