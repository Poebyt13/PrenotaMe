package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UtilsService {
    @GET("categories")
    Call<ApiResponse<List<Category>>> getCategories();



}
