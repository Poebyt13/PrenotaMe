package com.example.appprenotame.network.models.response;

import java.time.LocalDateTime;

public class EventData {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer categoryId;
    private String location;
    private Integer seatsTotal;
    private Integer seatsAvailable;
    private Integer createdBy;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String userPhoto;
}
