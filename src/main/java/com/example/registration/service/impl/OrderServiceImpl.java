package com.example.registration.service.impl;

import com.example.registration.model.Order;
import com.example.registration.model.Room;
import com.example.registration.model.UserEntity;
import com.example.registration.repository.OrderRepository;
import com.example.registration.repository.RoomRepository;
import com.example.registration.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RoomRepository roomRepository) {
        this.orderRepository = orderRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Order createOrder(UserEntity user, List<Long> roomId) {
        Order order = new Order();
        order.setUser(user);
        List<Room> roomList = getRoomById(roomId);
        order.setRoom((Room) roomList);
        return null;
    }

    private List<Room> getRoomById(List<Long> roomId){
        return roomId.stream()
                .map(roomRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addRooms(Order order, List<Long> roomId) {
        List<Room> rooms = (List<Room>) order.getRoom();
        List<Room> newRoomList = rooms == null ? new ArrayList<>() : new ArrayList<>(rooms);
        newRoomList.addAll(getRoomById(roomId));
        //TODO Casting 'newRoomList' to 'Room' will produce 'ClassCastException' for any non-null value
        order.setRoom((Room) newRoomList);
        orderRepository.save(order);
    }
}
