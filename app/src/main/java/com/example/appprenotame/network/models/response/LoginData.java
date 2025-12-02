package com.example.appprenotame.network.models.response;

import lombok.Getter;

@Getter
public class LoginData {
    private int id;
    private String email;
    private String username;
    private String description;
    private int is_admin;
    private String created_at;

    @Override
    public String toString() {
        return "LoginData{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", is_admin=" + is_admin +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
