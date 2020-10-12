package com.example.tianqi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tianqi.utils.Contents;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public class LocationDBHelper extends SQLiteOpenHelper {

    public LocationDBHelper(@Nullable Context context) {
        super(context,Contents.LOCATION_DB, null, Contents.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "Create table " + Contents.LOCATION_TABLE + "(" +
                Contents.ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                Contents.CITY + " varchar, " +
                Contents.WEA + " varchar, " +
                Contents.HIGH_TEAM + " integer, " +
                Contents.LOW_TEAM+ " integer, " +
                Contents.LATITUDE + " integer, " +
                Contents.LONGITUDE + " integer " + ")";
        sqLiteDatabase.execSQL(sql);


        String sqlCache = "Create table " + Contents.WEATHER_CACHE + "(" +
                Contents.ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                Contents.CITY + " varchar, " +
                Contents.WEA_DESCRIBE + " varchar, " +
                Contents.TF_DATA + " varchar, " +
                Contents.TF_QUALITY + " varchar, " +
                Contents.TF_WINDY + " varchar, " +
                Contents.TF_TIME + " varchar, " +
                Contents.TF_TEAM + " varchar, " +
                Contents.TF_WEA_ICON + " varchar, " +
                Contents.RAIN_STATE + " varchar, " +
                Contents.FIVE_WEA + " varchar, " +
                Contents.LIFE_INDEX + " varchar " + ")";
        sqLiteDatabase.execSQL(sqlCache);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+Contents.WEATHER_CACHE);
        sqLiteDatabase.execSQL("drop table if exists "+Contents.LOCATION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
