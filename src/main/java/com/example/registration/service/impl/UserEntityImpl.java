package com.example.registration.service.impl;

import com.example.registration.model.Room;
import com.example.registration.model.UserEntity;
import com.example.registration.repository.RoleRepository;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class UserEntityImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserEntityImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity result = userRepository.findByUsername(username);
        log.info("In findByUsername - user {} ", result, username);
        return result;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public UserEntity findById(Long id) {
        UserEntity result = userRepository.findById(id).orElse(null);

        if (result == null){
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User could not be delete"));
        userRepository.delete(user);
        log.info("IN delete - user with id: {} successfully deleted");
    }

    @Override
    public void save(UserEntity user) {
        log.info("IN userServiceImpl save {} " + user);
        userRepository.save(user);
    }
}
