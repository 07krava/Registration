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
    public Order createOrder(User user, List<Long> housingId) {
        Order order = new Order();
        order.setUser(user);
        List<Housing> housingList = getHousingById(housingId);
        order.setHousing((Housing) housingList);
        return null;
    }

    private List<Housing> getHousingById(List<Long> housingId){
        return housingId.stream()
                .map(housingRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addHousings(Order order, List<Long> housingId) {
        List<Housing> housings = (List<Housing>) order.getHousing();
        List<Housing> newHousingList = housings == null ? new ArrayList<>() : new ArrayList<>(housings);
        newHousingList.addAll(getHousingById(housingId));
        //TODO Casting 'newRoomList' to 'Room' will produce 'ClassCastException' for any non-null value
        order.setHousing((Housing) newHousingList);
        orderRepository.save(order);
    }
}
