package com.example.registration.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private String phone;
    private String email;
}
