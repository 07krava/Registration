package com.example.registration.service.impl;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.Room;
import com.example.registration.repository.RoomRepository;
import com.example.registration.service.OrderService;
import com.example.registration.service.RoomService;
import com.example.registration.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, UserService userService, OrderService orderService) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> result = roomRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Room findById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);

        if (room == null) {
            log.warn("IN findById - no room found by id: {}", id);
            return null;
        }
        log.info("IN findById - room: {} found by id: {}", room);
        return room;
    }

    @Override
    public void save(Room room) {
        log.info("IN roomServiceImpl save {} " + room);
        roomRepository.save(room);;
    }

    @Override
    public void deleteRoomId(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User could not be delete"));
        roomRepository.delete(room);
        log.info("IN delete - room with id: {} successfully deleted");
    }

    @Override
    @Transactional
    public void addToUserOrder(Long roomId, String username) {
        Optional<UserDTO> user = Optional.ofNullable(userService.findByUsername(username));
        if (user.isPresent()) {
            throw new UsernameNotFoundException("User not found - " + username);
        }
//
//        Order order = user.get();
//        if (order == null){
//            Order newOrder = orderService.createOrder(user.get(), Collections.singletonList(roomId));
//            user.setOrder(newOrder);
//            userService.save(user.get());
//        }else {
//            orderService.addRooms(order, Collections.singletonList(roomId));
//        }
    }
}
