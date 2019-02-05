package com.example.huzj.weather.util;

import android.text.TextUtils;

import com.example.huzj.weather.db.City;
import com.example.huzj.weather.db.County;
import com.example.huzj.weather.db.Province;
import com.example.huzj.weather.gson.AQI;
import com.example.huzj.weather.gson.Basic;
import com.example.huzj.weather.gson.Forecast;
import com.example.huzj.weather.gson.Now;
import com.example.huzj.weather.gson.Suggestion;
import com.example.huzj.weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/1 0001.
 */

public class Utility {
    /**
     * 处理返回的省级数据
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject provinceObj = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObj.getString("name"));
                    province.setProviceCode(provinceObj.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理返回的城市数据
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                System.out.println("data="+response);
                JSONArray allCities = new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    JSONObject cityObj = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObj.getString("name"));
                    city.setCityCode(cityObj.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理返回的县数据
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    JSONObject countyObj = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObj.getString("name"));
                    county.setWeatherId(countyObj.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析返回的天气信息
     * @param response
     * @return
     */
    public static Weather handWeatherResponse(String response){
        try {
            JSONObject weatherObj = new JSONObject(response);
           /* JSONArray heWeather = weatherObj.getJSONArray("HeWeather");
            String weatherContent = heWeather.getJSONObject(0).toString();*/
            JSONArray heWeather = weatherObj.getJSONArray("data");

            JSONObject weatherContent = heWeather.getJSONObject(0);
            System.out.println("weatherContent==="+weatherContent);
            Basic basic = new Basic();
            basic.cityName=weatherObj.getString("city");
            basic.weatherId=weatherObj.getString("cityid");
            basic.updateTime=weatherObj.getString("update_time");

            Now now = new Now();
            now.info=weatherContent.getString("wea");
            now.temperature=weatherContent.getString("tem");

            AQI aqi = new AQI();
            aqi.aqi=weatherContent.getString("air");
            aqi.pm25=weatherContent.getString("air_level");

            Suggestion suggestion = new Suggestion();
            suggestion.sportInfo=weatherContent.getString("air_tips");
            suggestion.carWashInfo=weatherContent.getJSONArray("index").getJSONObject(4).get("desc").toString();
            suggestion.comfortInfo=weatherContent.getJSONArray("index").getJSONObject(3).get("desc").toString();

            List<Forecast> forecastList=new ArrayList<>();
            JSONObject weatherContent1 = heWeather.getJSONObject(1);
            Forecast forecast1 = new Forecast();
            forecast1.date=weatherContent1.getString("date");
            forecast1.moreInfo=weatherContent1.getString("wea");
            forecast1.max=weatherContent1.getString("tem1");
            forecast1.min=weatherContent1.getString("tem2");
            JSONObject weatherContent2 = heWeather.getJSONObject(1);
            Forecast forecast2 = new Forecast();
            forecast2.date=weatherContent2.getString("date");
            forecast2.moreInfo=weatherContent2.getString("wea");
            forecast2.max=weatherContent2.getString("tem1");
            forecast2.min=weatherContent2.getString("tem2");
            forecastList.add(forecast1);
            forecastList.add(forecast2);

            Weather weather = new Weather();
            weather.suggestion=suggestion;
            weather.basic=basic;
            weather.aqi=aqi;
            weather.now=now;
            weather.basic=basic;
            weather.status="ok";
            weather.forecastList=forecastList;

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
