package com.example.pracainzynierska.model;

import androidx.annotation.NonNull;

public class EventRequest {

    @NonNull
    private String event_text;

    @NonNull
    private String color;

    @NonNull
    private Long user_id;

    @NonNull
    private String date;

    public EventRequest(@NonNull Long user_id, @NonNull String  event_text, @NonNull String color,  @NonNull String date) {
        this.user_id = user_id;
        this.event_text =  event_text;
        this.color = color;
        this.date = date;
    }

    @NonNull
    public String getEvent_text() {
        return event_text;
    }

    public void setEvent_text(@NonNull String event_text) {
        this.event_text = event_text;
    }

    @NonNull
    public String getColor() {
        return color;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

    @NonNull
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull Long user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
