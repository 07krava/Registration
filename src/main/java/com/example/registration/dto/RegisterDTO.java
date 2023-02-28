package com.example.registration.dto;

import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

//TODO use getter and setter annotation and remove all get and set methods

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Role role;


    public static RegisterDTO convertToDTO(User user) {
        return RegisterDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
