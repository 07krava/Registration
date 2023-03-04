package com.example.registration.dto;

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
    private String email;
    private String phone;
    private String password;

    public User convertToEntity(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        return user;
    }

    public static UserDTO convertToDTO(User user){
       return UserDTO.builder()
               .email(user.getEmail())
               .username(user.getUsername())
               .phone(user.getPhone())
               .build();

//
//        UserDTO userDto = new UserDTO();
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setPhone(user.getPhone());
//        userDto.setEmail(user.getEmail());
//        return userDto;
    }
}
