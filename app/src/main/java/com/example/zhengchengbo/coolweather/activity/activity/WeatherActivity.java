package com.example.zhengchengbo.coolweather.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhengchengbo.coolweather.R;
import com.example.zhengchengbo.coolweather.activity.util.HttpCallBackListener;
import com.example.zhengchengbo.coolweather.activity.util.HttpUtil;
import com.example.zhengchengbo.coolweather.activity.util.Utilist;

import butterknife.InjectView;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {
    private LinearLayout weatherInfoLayout;
    @InjectView(R.id.city_name)
    private TextView cityName;
    @InjectView(R.id.publish_text)
    private TextView publishText;
    @InjectView(R.id.weather_desp)
    private TextView weatherDespText;

    @InjectView(R.id.temp1)
    private TextView temp1;
    @InjectView(R.id.temp2)
    private TextView temp2;
    @InjectView(R.id.current_date)
    private TextView currentDateText;
    @InjectView(R.id.button)
    private Button switchCity;
    @InjectView(R.id.button2)
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            publishText.setText("同步中");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityName.setVisibility(View.INVISIBLE);
            quertWeatherCode(countyCode);
        } else {
            showWeather();
        }

    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityName.setText(prefs.getString("city_name", ""));
        temp1.setText(prefs.getString("temp1", ""));
        temp2.setText(prefs.getString("temp2", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDateText.setText(prefs.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityName.setVisibility(View.VISIBLE);
    }

    private void quertWeatherCode(String countyCode) {
        String address = "http://weather.com.cn/datalist3/city" + countyCode + "/xml";
        queryFromServer(address, "countyCode");
    }

    private void queryFromServer(String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    String[] array = response.split("\\|");
                    if (array != null && array.length == 2) {
                        String weatherCode = array[1];
                        quertWeatherInfo(weatherCode);
                    }
                } else if ("weatherCode".equals(type)) {
                    Utilist.handleWeatherReponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }


    @OnClick({R.id.button, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, ChooseActivity.class);
                intent.putExtra("form_weather_activity", toString());
                startActivity(intent);
                finish();
                break;
            case R.id.button2:
                publishText.setText("同步中。。。。。");
                SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = pres.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    quertWeatherInfo(weatherCode);
                }
                break;
            default:
                break;
        }
    }

    private void quertWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityInfo/" + weatherCode + ".xml";
        queryFromServer(address, "weatherCode");
    }

}
