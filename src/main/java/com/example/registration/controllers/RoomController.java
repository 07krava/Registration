package com.example.registration.controllers;

import com.example.registration.model.Order;
import com.example.registration.model.Room;
import com.example.registration.model.User;
import com.example.registration.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "allRooms")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();

        if (rooms.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK) ;
    }

    @GetMapping(value = "room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(name = "id") Long roomId){
        if (roomId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Room room = roomService.findById(roomId);

        if (room == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    //TODO refactor
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> addOrder(@PathVariable Long id, User user){
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomService.addToUserOrder(id, user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
