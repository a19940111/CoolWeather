package com.example.zhengchengbo.coolweather.activity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zhengchengbo.coolweather.activity.util.DBTable;

/**
 * Created by zhengchengbo on 2016/7/18.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBTable.PROVICE);
        db.execSQL(DBTable.CITY);
        db.execSQL(DBTable.COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
