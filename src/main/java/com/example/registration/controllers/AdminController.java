package com.example.registration.controllers;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.service.HousingService;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AdminController(UserService userService, HousingService housingService) {
        this.housingService = housingService;
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        UserDTO user = userService.findUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}