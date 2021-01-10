package com.example.pracainzynierska.model;

import androidx.annotation.NonNull;

public class HabitRequest {

    @NonNull
    private String habit_text;

    @NonNull
    private String color;

    @NonNull
    private Long user_id;

    @NonNull
    private String icon;

    public HabitRequest(@NonNull String habit_text, @NonNull String color, @NonNull Long user_id, @NonNull String icon){
        this.habit_text=habit_text;
        this.color=color;
        this.user_id=user_id;
        this.icon=icon;
    }

    @NonNull
    public String getHabit_text() {
        return habit_text;
    }

    public void setHabit_text(@NonNull String habit_text) {
        this.habit_text = habit_text;
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
    public String getIcon() {
        return icon;
    }

    public void setIcon(@NonNull String icon) {
        this.icon = icon;
    }
}
