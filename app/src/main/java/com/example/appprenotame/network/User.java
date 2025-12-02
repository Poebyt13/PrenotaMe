package com.example.appprenotame.network;

import lombok.Getter;

@Getter
public class User {
    private int id;
    private String username;
    private String email;
    private String description;
    public User(int id, String username, String email, String description) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
    }

}
