package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

public class HourlyModel {




    private WeatherDetailModel weatherDetailModel;
    @SerializedName("pop")
    private double pop;


    public WeatherDetailModel getWeatherDetailModel() {
        return weatherDetailModel;
    }

    public HourlyModel setWeatherDetailModel(WeatherDetailModel weatherDetailModel) {
        this.weatherDetailModel = weatherDetailModel;
        return this;
    }

    public double getPop() {
        return pop;
    }

    public HourlyModel setPop(double pop) {
        this.pop = pop;
        return this;
    }
}
