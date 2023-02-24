package com.example.registration.controllers;

import com.example.registration.dto.AdminUserDao;
import com.example.registration.dto.RoomDto;
import com.example.registration.model.Room;
import com.example.registration.model.UserEntity;
import com.example.registration.service.RoomService;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public AdminController(UserService userService, RoomService roomService) {
        this.roomService = roomService;
        this.userService = userService;
    }

        @GetMapping(value = "users/{id}")
        public ResponseEntity<AdminUserDao> getUserById(@PathVariable(name = "id") Long id){
            UserEntity user = userService.findById(id);

            if (user == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            AdminUserDao result = AdminUserDao.fromUserEntity(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        @GetMapping(value = "allUsers")
        public ResponseEntity <List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.getAll();

        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK) ;
        }

        @DeleteMapping(value = "delete/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }

    @PostMapping("addRoom")
    public ResponseEntity<String> createRoom(@RequestBody RoomDto roomDto){

        if (roomDto == null){
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
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long id, @RequestBody Room rooms){
        Room room = roomService.findById(id);

        if (room == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        room.setTitle(rooms.getTitle());
        room.setNumber(rooms.getNumber());
        room.setPrice(rooms.getPrice());

        roomService.save(room);

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteRoom/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long id){
        roomService.deleteRoomId(id);
        return new ResponseEntity<>("Room deleted", HttpStatus.OK);
    }
}
