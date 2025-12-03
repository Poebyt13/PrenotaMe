package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UtilsService {
    @GET("categories")
    Call<ApiResponse<List<Category>>> getCategories();

    @GET("categories/{id}")
    Call<ApiResponse<Category>> getCategoryById(@Path("id") int id);


}
