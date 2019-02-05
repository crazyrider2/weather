package com.example.huzj.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/2/2 0002.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("wea")
    public String info;
   /* @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }*/
}
