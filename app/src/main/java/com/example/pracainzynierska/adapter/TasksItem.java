package com.example.pracainzynierska.adapter;


public class TasksItem {
    private long id;
    private String mText1;
    private String color;
    private String mText3;
    private boolean isDone;

    public TasksItem(long id,String text1, String color, String text3, boolean isDone){
        this.id=id;
        this.mText1=text1;
        this.color=color;
        this.mText3=text3;
        this.isDone=isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor(){
        return color;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText3() { return mText3; }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
