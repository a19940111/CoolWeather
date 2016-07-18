package com.example.zhengchengbo.coolweather.activity.util;

import android.text.TextUtils;

import com.example.zhengchengbo.coolweather.activity.model.City;
import com.example.zhengchengbo.coolweather.activity.model.CoolWeatherDB;
import com.example.zhengchengbo.coolweather.activity.model.County;
import com.example.zhengchengbo.coolweather.activity.model.Province;

/**
 * Created by zhengchengbo on 2016/7/18.
 */
public class Utilist {
    public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB, String respoinse) {
        if (!TextUtils.isEmpty(respoinse)) {
            String[] allProvince = respoinse.split(",");
            if (allProvince != null && allProvince.length > 0) {
                for (String p : allProvince) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }

        }
        return false;
    }
    public static boolean handleCityReponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String allCities[]=response.split(",");
            if(allCities!=null&&allCities.length>0){
                for(String c:allCities){
                    String[] array=c.split("\\|");
                    City city=new City();
                    city.setProvinceId(provinceId);
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCountyReponse(CoolWeatherDB coolWeatherDB,String reponse,int cityId){
        if(!TextUtils.isEmpty(reponse)){
            String[]  allCounties=reponse.split(",");
            if(allCounties.length>0&&allCounties!=null){
                for(String c:allCounties){
                    String []array=c.split("\\|");
                    County county=new County();
                    county.setCityId(cityId);
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    coolWeatherDB.saveCounty(county);
                }
            }
            return true;
        }
        return false;
    }
}
