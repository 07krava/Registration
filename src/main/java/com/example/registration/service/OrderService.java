package com.example.registration.service;

import com.example.registration.model.Order;
import com.example.registration.model.UserEntity;

import java.util.List;

public interface OrderService {

    Order createOrder(UserEntity user, List<Long> roomId);

    void addRooms(Order order, List<Long> roomId);
}
