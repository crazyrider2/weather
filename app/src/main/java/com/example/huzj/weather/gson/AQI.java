package com.example.huzj.weather.gson;

/**
 * Created by Administrator on 2019/2/2 0002.
 */

public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
