package com.example.huzj.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/2/2 0002.
 */

public class Suggestion {

    public String sportInfo;
    @SerializedName("comf")
    public String comfortInfo;
    @SerializedName("cw")
    public String carWashInfo;
    /*public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }

    public class Sport{
        @SerializedName("txt")
        public String info;
    }*/
}
