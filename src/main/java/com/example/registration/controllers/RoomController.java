package com.example.registration.controllers;

import com.example.registration.dto.RoomDto;
import com.example.registration.model.Order;
import com.example.registration.model.Room;
import com.example.registration.model.UserEntity;
import com.example.registration.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "allRooms")
    public ResponseEntity<List<Room>> getAllUsers(){
        List<Room> rooms = roomService.getAllRooms();

        if (rooms.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK) ;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Room> getUserById(@PathVariable(name = "id") Long roomId){
        if (roomId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Room room = roomService.findById(roomId);

        if (room == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping("/order{id}")
    public ResponseEntity<Order> addOrder(@PathVariable Long id, UserEntity user){
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomService.addToUserOrder(id, user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
