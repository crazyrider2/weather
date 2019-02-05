package com.example.huzj.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/2/2 0002.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("cityid")
    public String weatherId;
    @SerializedName("update_time")
    public String updateTime;
    /*public Update update;
    public class Update{
        @SerializedName("hours")
        public String updateTime;
    }*/
}
