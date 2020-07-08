package com.ziko.isaac.recyclerviewanimation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "flowers_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE flowers_table (CREATOR TEXT, IMAGE_URL TEXT, LIKES TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long onInsert(String s_creator, String s_img_url, int s_likes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("creator", s_creator);
        cv.put("image_url", s_img_url);
        cv.put("likes", s_likes);

        long result = db.insert("flowers_table", null, cv);
        return result;
    }

    public Cursor readAllDataFromDB(){
        String query = "SELECT * FROM flowers_table";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
    return cursor;
    }
}
