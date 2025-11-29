package com.example.appprenotame.network.models.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String messagge;

}
