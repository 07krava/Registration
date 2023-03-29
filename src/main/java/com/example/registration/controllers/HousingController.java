package com.example.registration.controllers;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.ImageDTO;
import com.example.registration.service.HousingService;
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

    @GetMapping("/getImage/{id}")
    public List<ImageDTO> getImages(@PathVariable Long id) {
        return housingService.getImagesByHousingId(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHousing(@PathVariable Long id) {
        housingService.deleteHousing(id);
        return new ResponseEntity<>("Housing "+ id + " delete successfully!", HttpStatus.OK);
    }

    @GetMapping("/getHousing/{id}")
    public HousingDTO getHousingById(@PathVariable Long id) {
        return housingService.getHousingById(id);
    }

}
