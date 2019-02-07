package com.example.huzj.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2019/1/31 0031.
 */

public class Province extends DataSupport {
    private String provinceName;
    private int proviceCode;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProviceCode() {
        return proviceCode;
    }

    public void setProviceCode(int proviceCode) {
        this.proviceCode = proviceCode;
    }
}
