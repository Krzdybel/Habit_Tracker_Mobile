package com.example.pracainzynierska;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.pracainzynierska.ui.MainActivity;

import java.util.HashMap;

public class SessionManager{

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;


    public String PREF_NAME = "LOGIN";
    public String LOGIN = "IS_LOGIN";
    public String NAME = "NAME";
    public String EMAIL = "EMAIL";



    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();

        //Intent i = new Intent(context, LoginActivity.class);
        //context.startActivity(i);
        //((MainActivity) context).finish();
    }

}