package com.example.huzj.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/2/2 0002.
 */

public class Forecast {

    public String date;
    @SerializedName("cond")
    public String moreInfo;
    public String max;
    public String min;
    /*public class More{
        @SerializedName("txt_d")
        public String info;
    }
    public class Temperature{
        public String max;
        public String min;
    }*/
}
