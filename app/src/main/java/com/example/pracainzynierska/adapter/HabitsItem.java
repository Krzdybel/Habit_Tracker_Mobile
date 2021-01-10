package com.example.pracainzynierska.adapter;

public class HabitsItem {
    private long id;
    private String mTextName;
    private String color;
    private String image;
    private boolean isDone;

    public HabitsItem(long id, String mTextName, String color, String image, boolean isDone){
        this.id=id;
        this.mTextName=mTextName;
        this.color=color;
        this.image=image;
        this.isDone=isDone;
    }

    public long getId() {
        return id;
    }

    public String getmTextName() {
        return mTextName;
    }

    public String getColor() {
        return color;
    }

    public String getImage() {
        return image;
    }

    public boolean isDone(){
        return isDone;
    }
}
