package com.example.pracainzynierska.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TaskResponse {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("task_text")
    @Expose
    private String task_text;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("user_id")
    @Expose
    private long user_id;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("done")
    @Expose
    private boolean isDone;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask_text() {
        return task_text;
    }

    public void setTask_text(String task_text) {
        this.task_text = task_text;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
