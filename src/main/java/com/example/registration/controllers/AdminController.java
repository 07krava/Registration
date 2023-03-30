package com.example.registration.controllers;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.service.HousingService;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final UserService userService;
    private final HousingService housingService;

    public AdminController(UserService userService, HousingService housingService) {
        this.userService = userService;
        this.housingService = housingService;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        //TODO rewrite using optional.isPresent or optional.ifPresent
        Optional<User> user = userService.findUserById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }



    @DeleteMapping(value = "deleteRoom/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long id) {
        housingService.deleteHousing(id);
        return new ResponseEntity<>("Room deleted", HttpStatus.OK);
    }
}
