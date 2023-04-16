package com.example.registration.controllers;

import com.example.registration.dto.HousingDTO;
import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.service.HousingService;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    //TODO please do all this instructions in every files, you can do it any time and before commit too
    // 1) reformat all files in project got to -> Code -> Reformat code
    // 2) optimize imports go to Code -> Optimize imports
    // 3) after finishing making task which is described in todo, you can remove it(just delete TODO comments)
    private final UserService userService;
    private final HousingService housingService;

    @Autowired
    public AdminController(UserService userService, HousingService housingService) {
        this.housingService = housingService;
        this.userService = userService;
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDTO result = UserDTO.convertToDTO(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HousingDTO> createHousing(@ModelAttribute HousingDTO housing, @RequestParam("file") MultipartFile[] files) throws IOException, IOException {
        HousingDTO newHousing = housingService.createHousing(housing, files);
        return new ResponseEntity<>(newHousing, HttpStatus.OK);
    }

    @PutMapping("/updateHousing/{id}")
    public HousingDTO updateHousing(@PathVariable Long id, @ModelAttribute HousingDTO housingDTO, @RequestParam("file") MultipartFile[] files) throws IOException {
        housingDTO.setId(id);
        return housingService.updateHousing(id, housingDTO, files);
    }

    @DeleteMapping("/deleteHousing/{id}")
    public ResponseEntity<String> deleteHousingById(@PathVariable Long id) {
        housingService.deleteHousing(id);
        return new ResponseEntity<>("Housing "+ id + " delete successfully!", HttpStatus.OK);
    }
}
