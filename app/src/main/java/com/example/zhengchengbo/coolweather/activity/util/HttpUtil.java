package com.example.zhengchengbo.coolweather.activity.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhengchengbo on 2016/7/18.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                    if(listener!=null) {
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
