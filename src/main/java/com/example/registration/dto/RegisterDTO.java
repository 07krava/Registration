package com.example.registration.dto;

import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO use getter and setter annotation and remove all get and set methods

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
