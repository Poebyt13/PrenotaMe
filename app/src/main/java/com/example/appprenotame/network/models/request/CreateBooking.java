package com.example.appprenotame.network.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBooking {
    private String eventId;
    private String userId;

}
