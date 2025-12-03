package com.example.appprenotame.network.models.api;

import com.example.appprenotame.network.models.request.EventRequest;
import com.example.appprenotame.network.models.request.UpdateEventRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.DeleteEventData;
import com.example.appprenotame.network.models.response.EventData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {
    @GET("events")
    Call<ApiResponse<List<EventData>>> getEvents();

    @POST("events")
    Call<ApiResponse<EventData>> createEvent(@Body EventRequest request);

    @GET("events/{id}")
    Call<ApiResponse<EventData>> getEventById(@Path("id") int id);

    @DELETE("events/{id}")
    Call<ApiResponse<DeleteEventData>> deleteEvent(@Path("id") int id);

    @PUT("events/{id}")
    Call<ApiResponse<EventData>> updateEvent(@Path("id") int id, @Body UpdateEventRequest request);
}
