package com.example.pracainzynierska.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HabitResponse {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("habit_text")
    @Expose
    private String habit_text;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("user_id")
    @Expose
    private long user_id;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("done")
    @Expose
    private boolean isDone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHabit_text() {
        return habit_text;
    }

    public void setHabit_text(String habit_text) {
        this.habit_text = habit_text;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
