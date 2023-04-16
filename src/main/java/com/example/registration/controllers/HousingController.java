package com.example.registration.controllers;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import com.example.registration.service.HousingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/housing")
public class HousingController {

    @Autowired
    private HousingService housingService;

    @PostMapping("/add")
    public ResponseEntity<HousingDTO> createHousing(@ModelAttribute HousingDTO housing, @RequestParam("file") MultipartFile[] files) throws IOException, IOException {
        HousingDTO newHousing = housingService.createHousing(housing, files);
        return new ResponseEntity<>(newHousing, HttpStatus.OK);
    }

    @GetMapping("/allHousing")
    public List<HousingDTO> getAllHousing() {
        return housingService.getAllHousing();
    }

    @PutMapping("/updateHousing/{id}")
    public HousingDTO updateHousing(@PathVariable Long id, @ModelAttribute HousingDTO housingDTO, @RequestParam("file") MultipartFile[] files) throws IOException {
        housingDTO.setId(id);
        return housingService.updateHousing(id, housingDTO, files);
    }

    @DeleteMapping("/deleteHousing/{id}")
    public ResponseEntity<String> deleteHousingById(@PathVariable Long id) {
        housingService.deleteHousingById(id);
        return new ResponseEntity<>("Housing "+ id + " delete successfully!", HttpStatus.OK);
    }

    @GetMapping("/getHousing/{id}")
    public HousingDTO getHousingById(@PathVariable Long id) {
        return housingService.getHousingById(id);
    }

    @GetMapping("/getImagesByHousing/{id}")
    public List<ImageDTO> getImagesByHousingId(@PathVariable Long id){
        return housingService.getImagesByHousingId(id);
    }
    @GetMapping("/{housingId}/image/{imageId}")
    public ResponseEntity<ImageDTO> getImageByIdFromHousingId(@PathVariable Long housingId, @PathVariable Long imageId) {
        HousingDTO housing = housingService.getHousingById(housingId);
        ImageDTO imageDTO = null;
        for (ImageDTO p : housing.getImages()) {
            if (p.getId().equals(imageId)) {
                imageDTO = p;
                break;
            }
        }
        if (imageDTO == null) {
            throw new EntityNotFoundException("Photo does not exist with this ID: " + imageId);
        }
        return ResponseEntity.ok(imageDTO);
    }

    @DeleteMapping("/{housingId}/deleteImage/{imageId}")
    public ResponseEntity<Void> deleteImageByIdFromHousingId(@PathVariable Long housingId, @PathVariable Long imageId) {
        housingService.deleteImageByIdFromHousingId(housingId, imageId);
        return ResponseEntity.noContent().build();
    }
}


