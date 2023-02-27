package com.example.registration.service;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    //Todo please use Optional<ClassName> methodName, methods where you are using collections leave as it is
    // Example
    // ->  User findByUsername(String username);
    // -> Optional<User> findByUsername(String username);
    User findByUsername(String username);

    //TODO посмотреть как сделать через ifPresent() Java8
    UserDTO loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getAll();

    User findById(Long id);

    void delete(Long id);

    void save(User user);
}