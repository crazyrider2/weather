package com.example.huzj.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2019/1/31 0031.
 */

public class County extends DataSupport {
    private int countyCode;
    private String countyName;
    private int cityId;

    public int getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(int countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
