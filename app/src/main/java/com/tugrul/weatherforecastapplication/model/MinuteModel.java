package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

public class MinuteModel {



    @SerializedName("dt")
    private int dt;
    @SerializedName("precipitation")
    private int precipitation;


    public int getDt() {
        return dt;
    }

    public MinuteModel setDt(int dt) {
        this.dt = dt;
        return this;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public MinuteModel setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
        return this;
    }
}
