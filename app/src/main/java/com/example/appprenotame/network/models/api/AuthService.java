package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.request.LoginRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.LoginData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<ApiResponse<LoginData>> login(@Body LoginRequest request);
}
