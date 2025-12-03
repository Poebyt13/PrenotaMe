package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.request.CompleteProfileRequest;
import com.example.appprenotame.network.models.request.LoginRequest;
import com.example.appprenotame.network.models.request.RegisterRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CompleteProfileData;
import com.example.appprenotame.network.models.response.LoginData;
import com.example.appprenotame.network.models.response.RegisterData;
import com.example.appprenotame.network.models.response.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {
    @POST("auth/login")
    Call<ApiResponse<LoginData>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<RegisterData>> register(@Body RegisterRequest request);

    @POST("auth/complete-profile")
    Call<ApiResponse<CompleteProfileData>> completeProfile(@Body CompleteProfileRequest request);

    @GET("auth/user/{id}")
    Call<ApiResponse<UserData>> getUserById(@Path("id") int id);
}
