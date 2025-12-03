package com.example.appprenotame.network.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEventRequest {
    private String title;
    private String description;
    private String image_url;
}
