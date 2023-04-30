package com.example.registration.dto;

import com.example.registration.model.Role;
import com.example.registration.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private Role role;

    public static UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User fromDto(UserDTO dao) {
        return User.builder()
                .id(dao.getId())
                .username(dao.getUsername())
                .phone(dao.getPhone())
                .email(dao.getEmail())
                .password(dao.getPassword())
                .role(dao.getRole())
                .build();
    }

}