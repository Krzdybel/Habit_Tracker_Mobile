package com.example.pracainzynierska;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TasksDatabaseHelper extends SQLiteOpenHelper {

    public Context context;
    public static final String DATABASE_NAME = "Tasks.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "task_name";
    public static final String COLUMN_DESC = "task_desc";
    public static final String COLUMN_DATE = "task_date";

    public TasksDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_DATE + " TEXT); ";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

    }

    public void addTask(String name, String desc, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_DATE, date);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

    public void updateData(String id, String name, String desc, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_DATE, date);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "UPDATED", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {id});
        //db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "SUCCESSFULLY DELETED", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id", null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        if(result == -1){
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "DELETED ALL ITEMS", Toast.LENGTH_SHORT).show();
        }
    }


}
