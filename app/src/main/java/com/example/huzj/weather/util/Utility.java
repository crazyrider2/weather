package com.example.huzj.weather.util;

import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by Administrator on 2019/2/1 0001.
 */

public class Utility {

    /**
     * 解析返回的天气信息
     * @param response
     * @return
     */
    public static Weather handWeatherResponse(String response){
        try {
            JSONObject weatherObj = new JSONObject(response);
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
            forecast1.setDate(weatherContent1.getString("date"));
            forecast1.setMoreInfo(weatherContent1.getString("wea"));
            forecast1.setMax(weatherContent1.getString("tem1"));
            forecast1.setMin(weatherContent1.getString("tem2"));
            JSONObject weatherContent2 = heWeather.getJSONObject(1);
            Forecast forecast2 = new Forecast();
            forecast2.setDate(weatherContent2.getString("date"));
            forecast2.setMoreInfo(weatherContent2.getString("wea"));
            forecast2.setMax(weatherContent2.getString("tem1"));
            forecast2.setMin(weatherContent2.getString("tem2"));
            forecastList.add(forecast1);
            forecastList.add(forecast2);

            Weather weather = new Weather();
            weather.setSuggestion(suggestion);
            weather.setBasic(basic);
            weather.setAqi(aqi);
            weather.setNow(now);
            weather.setStatus("ok");
            weather.setForecastList(forecastList);
            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Province> getProvinces(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Province> provinceList=new ArrayList<>();
        try {
            //获取assets资源管理器
            AssetManager assetManager = getContext().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
            JSONArray allProvinces = new JSONArray(stringBuilder.toString());
            String tmp="";
            for (int i=0;i<allProvinces.length();i++){
                JSONObject provinceObj = allProvinces.getJSONObject(i);
                if (!(provinceObj.getString("provinceZh").equals(tmp))){
                    tmp=provinceObj.getString("provinceZh");
                    Province province = new Province();
                    province.setProvinceName(provinceObj.getString("provinceZh"));
                    province.setProviceCode(Integer.parseInt(new String(provinceObj.getString("id")).substring(0,6)));
                    provinceList.add(province);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    public static List<City> getCities(String provinceName){
        List<City> cityList=new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = getContext().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
            JSONArray allCities = new JSONArray(stringBuilder.toString());
            String tmp="";
            for (int i=0;i<allCities.length();i++){
                JSONObject cityObj = allCities.getJSONObject(i);
                if ((cityObj.getString("provinceZh").equals(provinceName))&&(!(cityObj.getString("leaderZh").equals(tmp)))){
                    tmp=cityObj.getString("leaderZh");
                    City city = new City();
                    city.setCityName(cityObj.getString("cityZh"));
                    city.setCityCode(cityObj.getInt("id"));
                    city.setProvinceId(Integer.parseInt(new String(cityObj.getString("id")).substring(0,6)));
                    cityList.add(city);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityList;
    }


    public static List<County> getCountise(String provinceName,int citiCode,String cityName){
        List<County> countyList=new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = getContext().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("city.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
            JSONArray allCounties = new JSONArray(stringBuilder.toString());
            for (int i=0;i<allCounties.length();i++){
                JSONObject countyObj = allCounties.getJSONObject(i);
                if ((countyObj.getString("provinceZh").equals(provinceName))&&(countyObj.getString("leaderZh").equals(cityName))){
                    County county = new County();
                    county.setCityId(citiCode);
                    county.setCountyName(countyObj.getString("cityZh"));
                    county.setCountyCode(countyObj.getInt("id"));
                    countyList.add(county);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countyList;
    }
}
