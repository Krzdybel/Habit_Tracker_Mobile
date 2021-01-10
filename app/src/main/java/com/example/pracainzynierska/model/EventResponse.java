package com.example.pracainzynierska.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EventResponse {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("event_name")
    @Expose
    private String event_name;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("user_id")
    @Expose
    private long user_id;

    @SerializedName("date")
    @Expose
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
