package com.example.appprenotame.network.models.request;

public class CompleteProfileRequest {
    private int id;
    private String username;
    private String description;

    public CompleteProfileRequest(int id, String username, String description) {
        this.id = id;
        this.username = username;
        this.description = description;
    }
}
