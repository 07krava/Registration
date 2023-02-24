package com.example.registration.service;

import com.example.registration.model.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();

    Room findById(Long id);

    void save(Room room);

    void deleteRoomId(Long id);

    void addToUserOrder(Long roomId, String username);
}
