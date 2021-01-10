package com.example.pracainzynierska.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class TaskRequest {

    @NonNull
    private String task_text;

    @NonNull
    private String color;

    @NonNull
    private Long user_id;

    @NonNull
    private String date;

    @NonNull
    private boolean isDone;


    public TaskRequest(@NonNull String task_text, @NonNull String color, @NonNull Long user_id, @NonNull String date) {
        this.task_text = task_text;
        this.color = color;
        this.user_id = user_id;
        this.date = date;
    }

    @NonNull
    public String getTask_text() {
        return task_text;
    }

    public void setTask_text(@NonNull String task_text) {
        this.task_text = task_text;
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

//    public boolean isDone() {
//        return isDone;
//    }
//
//    public void setDone(boolean done) {
//        isDone = done;
//    }
}

