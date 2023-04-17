package com.example.registration.service.impl;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserEntityImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserEntityImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO add convert entity UserDTO

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        UserDTO userDTO = UserDTO.convertToDTO(result.get());
        log.info("In findByUsername - user {} ", userDTO, username);
        return userDTO;
    }

    //TODO посмотреть как сделать через ifPresent() Java8
    @Override
    public Optional<UserDTO> loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()){
            return Optional.ofNullable(UserDTO.convertToDTO(user.get()));
        }else{
            throw new UsernameNotFoundException("User ".concat(username).concat(" not found"));
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null){
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", result);
        return Optional.of(result);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User could not be delete"));
        userRepository.delete(user);
        log.info("IN delete - user with id: {} successfully deleted");
    }

    @Override
    public void saveUser(User user) {
        log.info("IN userServiceImpl save {} " + user);
        userRepository.save(user);
    }
}
