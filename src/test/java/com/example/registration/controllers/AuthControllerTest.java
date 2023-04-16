package com.example.registration.controllers;

import com.example.registration.dto.AuthResponseDto;
import com.example.registration.dto.LoginDto;
import com.example.registration.dto.RegisterDTO;
import com.example.registration.model.Role;
import com.example.registration.repository.UserRepository;
import com.example.registration.security.JWTGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin() {
        // Given
        LoginDto loginDto = new LoginDto("username", "password", "0666666", "test@test.com");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String token = "jwtToken";
        when(jwtGenerator.generateToken(authentication)).thenReturn(token);

        // When
        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getAccessToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegisterUserSuccess() {
        RegisterDTO registerDto = new RegisterDTO("user1", "user1@gmail.com", "12345678", "password", Role.USER);

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<String> responseEntity = authController.register(registerDto);

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(responseEntity.getBody(), "User register success!");
    }

    @Test
    void testRegisterUserUsernameTaken() {
        RegisterDTO registerDto = new RegisterDTO("user1", "user1@gmail.com", "12345678", "password", Role.USER);

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        ResponseEntity<String> responseEntity = authController.register(registerDto);

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(responseEntity.getBody(), "Username is taken!");
    }
}
