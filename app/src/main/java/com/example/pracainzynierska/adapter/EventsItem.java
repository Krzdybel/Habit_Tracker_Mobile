package com.example.pracainzynierska.adapter;

public class EventsItem {
    private int mImageResource;
    //private boolean mCheckBox;
    private long id;
    private String color;
    private String mText1;
    private String mText2;

    public EventsItem(long id, String text1, String text2, String color){
        this.id=id;
        this.mText1=text1;
        this.mText2=text2;
        this.color=color;
    }

//    public EventsItem(int imageResource, String text1, String text2, String text3){
//        mImageResource=imageResource;
//        //mCheckBox=checkBox;
//        mText1=text1;
//        mText2=text2;
//        mText3=text3;
//    }

    public long getId() {
        return id;
    }

    public String getColor(){
        return color;
    }

//    public int getmImageResource() {
//        return mImageResource;
//    }

   // public boolean getmCheckBox() { return mCheckBox; }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }



}
