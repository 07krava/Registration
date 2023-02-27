package com.example.registration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    private String username;
    private String password;
    private String phone;
    private String email;
}
