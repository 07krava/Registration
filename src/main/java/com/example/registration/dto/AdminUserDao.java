package com.example.registration.dto;

import com.example.registration.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDao {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;

    public UserEntity toUser(){
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        return user;
    }

    public static AdminUserDao fromUserEntity(UserEntity user){
        AdminUserDao adminUserDao = new AdminUserDao();
        adminUserDao.setId(user.getId());
        adminUserDao.setUsername(user.getUsername());
        adminUserDao.setPhone(user.getPhone());
        adminUserDao.setEmail(user.getEmail());
        return adminUserDao;
    }
}
