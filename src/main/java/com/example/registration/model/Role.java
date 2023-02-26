package com.example.registration.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN(1, "ADMIN"),
    USER(2, "USER"),
    OWNER(3, "OWNER") ;

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}