package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.request.CompleteProfileRequest;
import com.example.appprenotame.network.models.request.LoginRequest;
import com.example.appprenotame.network.models.request.RegisterRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CompleteProfileData;
import com.example.appprenotame.network.models.response.LoginData;
import com.example.appprenotame.network.models.response.RegisterData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<ApiResponse<LoginData>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<RegisterData>> register(@Body RegisterRequest request);

    @POST("auth/complete-profile")
    Call<ApiResponse<CompleteProfileData>> completeProfile(@Body CompleteProfileRequest request);
}
