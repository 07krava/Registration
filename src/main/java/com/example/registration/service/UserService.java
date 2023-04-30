package com.example.registration.service;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO findByUsername(String username);

    Optional<UserDTO> loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getAllUsers();

    UserDTO findUserById(Long id);

    void deleteUser(Long id);

    void saveUser(User user);
}