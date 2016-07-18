package com.example.zhengchengbo.coolweather.activity.util;

/**
 * Created by zhengchengbo on 2016/7/18.
 */
public class DBTable {
    public static final String PROVICE="create table province(id integer primary key autoincrement" +
            ",province_name text,province_code text)";
    public static final String CITY="create table city(id integer primary key autoincrement," +
            "city_name text,city_code text,province_id integer)";
    public static final String COUNTY="create table county(id integer primary key autoincrement," +
            "county_name text,county_code text,city_id integer)";
}
