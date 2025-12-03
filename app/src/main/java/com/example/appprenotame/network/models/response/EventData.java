package com.example.appprenotame.network.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EventData {

    private int id;
    private String title;
    private String description;
    private String date_start;
    private String date_end;
    private String location;
    private int seats_total;
    private int seats_available;

    private int category_id;
    private String image_url;
    private String created_at;
    private int created_by;
    private String user_photo;


    @Override
    public String toString() {
        return "EventData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                ", location='" + location + '\'' +
                ", seats_total=" + seats_total +
                ", seats_available=" + seats_available +
                ", category_id=" + category_id +
                ", image_url='" + image_url + '\'' +
                ", created_at=" + created_at +
                ", created_by=" + created_by +
                ", userPhoto='" + user_photo + '\'' +
                '}';
    }
}

