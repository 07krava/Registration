package com.example.registration.service;

import com.example.registration.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findByUsername(String username);

    List<UserEntity> getAll();

    UserEntity findById(Long id);

    void delete(Long id);

    void save(UserEntity user);
}