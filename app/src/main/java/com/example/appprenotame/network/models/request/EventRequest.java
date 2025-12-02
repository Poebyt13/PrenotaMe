package com.example.appprenotame.network.models.request;

import java.time.LocalDateTime;
import lombok.Data;

public class EventRequest {
    private String title;
    private String description;
    private LocalDateTime date_start;
    private LocalDateTime date_end;
    private Integer category_id;
    private String location;
    private Integer seats_total;
    private Integer created_by;
    private String image_url;
}

