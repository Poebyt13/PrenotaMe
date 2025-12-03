package com.example.appprenotame.network.models.request;

import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class EventRequest {
    private String title;
    private String description;
    private String date_start;
    private String date_end;
    private int category_id;
    private String location;
    private int seats_total;
    private int created_by;
    private String image_url;

}

