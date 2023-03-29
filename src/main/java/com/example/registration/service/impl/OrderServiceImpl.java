package com.example.registration.service.impl;

import com.example.registration.model.Housing;
import com.example.registration.model.Order;
import com.example.registration.model.User;
import com.example.registration.repository.HousingRepository;
import com.example.registration.repository.OrderRepository;
import com.example.registration.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final HousingRepository housingRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, HousingRepository housingRepository) {
        this.orderRepository = orderRepository;
        this.housingRepository = housingRepository;
    }

    @Override
    public Order createOrder(User user, List<Long> roomId) {
        Order order = new Order();
        order.setUser(user);
        List<Housing> roomList = getRoomById(roomId);
        order.setRoom((Housing) roomList);
        return null;
    }

    private List<Housing> getRoomById(List<Long> roomId){
        return roomId.stream()
                .map(housingRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addRooms(Order order, List<Long> roomId) {

        List<Housing> rooms = (List<Housing>) order.getRoom();
        List<Housing> newRoomList = rooms == null ? new ArrayList<>() : new ArrayList<>(rooms);
        newRoomList.addAll(getRoomById(roomId));
        //TODO Casting 'newRoomList' to 'Room' will produce 'ClassCastException' for any non-null value
        order.setRoom((Housing) newRoomList);
        orderRepository.save(order);
    }
}
