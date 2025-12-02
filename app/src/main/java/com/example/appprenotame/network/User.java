package com.example.appprenotame.network;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String username;
    private String description;
    private String photo;
    private int isAdmin;
    private String createdAt;

}
