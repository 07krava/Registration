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

    private final UserService userService;
    private final HousingService housingService;

    @Autowired
    public AdminController(UserService userService, HousingService housingService) {
        this.housingService = housingService;
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        //TODO rewrite using optional.isPresent or optional.ifPresent
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDTO result = UserDTO.convertToDto(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    @PostMapping("addRoom")
    public ResponseEntity<String> createRoom(@RequestBody RoomDto roomDto) {

        if (roomDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Room room = new Room();
        room.setTitle(roomDto.getTitle());
        room.setNumber(roomDto.getNumber());
        room.setPrice(roomDto.getPrice());

        roomService.save(room);

        return new ResponseEntity<>("Room added success!", HttpStatus.OK);
    }

    @PutMapping(value = "updateRoom/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long id, @RequestBody Room rooms) {
        Room room = roomService.findById(id);

        if (room == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        room.setTitle(rooms.getTitle());
        room.setNumber(rooms.getNumber());
        room.setPrice(rooms.getPrice());

        roomService.save(room);

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteRoom/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long id) {
        roomService.deleteRoomId(id);
        return new ResponseEntity<>("Room deleted", HttpStatus.OK);
    }
}
