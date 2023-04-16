package com.example.registration.controllers;

import com.example.registration.dto.UserDTO;
import com.example.registration.model.User;
import com.example.registration.service.HousingService;
import com.example.registration.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HousingService housingService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testuser");

        when(userService.findById(userId)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> response = adminController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void testGetUserByIdWithInvalidId() {
        // Arrange
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserDTO> response = adminController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllUsersSuccess() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John"));
        when(userService.getAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getUsername());
    }

    @Test
    public void testGetAllUsersNotFound() {
        when(userService.getAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<User>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("User deleted", HttpStatus.OK);

        doNothing().when(userService).delete(userId);

        ResponseEntity<String> actualResponse = adminController.deleteUser(userId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}