package com.example.appprenotame.network.models.response;

import lombok.Getter;

@Getter
public class UserData {
    private int id;
    private String email;
    private String username;
    private String description;
    private String photo;
    private int is_admin;
    private String created_at;
}
