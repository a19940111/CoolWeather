package com.example.zhengchengbo.coolweather.activity.util;

/**
 * Created by zhengchengbo on 2016/7/18.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
