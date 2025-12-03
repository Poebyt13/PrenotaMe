package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.request.CreateBooking;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CreateBookingResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookingService {
    @POST("bookings")
    Call<ApiResponse<CreateBookingResponse>> createBooking(@Body CreateBooking request);
}
