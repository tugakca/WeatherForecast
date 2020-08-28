package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyModel {




    @SerializedName("dt")
    private int dt;
    @SerializedName("sunrise")
    private int sunrise;
    @SerializedName("sunset")
    private int sunset;
    @SerializedName("temp")
    private TempModel temp;
    @SerializedName("feelLike")
    private FeelLikeModel feelLike;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("dew_point")
    private double dew_point;
    @SerializedName("wind_speed")
    private double wind_speed;
    @SerializedName("wind_deg")
    private int wind_deg;
    @SerializedName("weather")
    private List<WeatherInfoModel> weather;
    @SerializedName("clouds")
    private int clouds;
    @SerializedName("pop")
    private double pop;
    @SerializedName("uvi")
    private double uvi;


    public TempModel getTemp() {
        return temp;
    }

    public DailyModel setTemp(TempModel temp) {
        this.temp = temp;
        return this;
    }

    public FeelLikeModel getFeelLike() {
        return feelLike;
    }

    public DailyModel setFeelLike(FeelLikeModel feelLike) {
        this.feelLike = feelLike;
        return this;
    }

    public List<WeatherInfoModel> getWeather() {
        return weather;
    }

    public DailyModel setWeather(List<WeatherInfoModel> weather) {
        this.weather = weather;
        return this;
    }

    public int getDt() {
        return dt;
    }

    public DailyModel setDt(int dt) {
        this.dt = dt;
        return this;
    }

    public int getSunrise() {
        return sunrise;
    }

    public DailyModel setSunrise(int sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public int getSunset() {
        return sunset;
    }

    public DailyModel setSunset(int sunset) {
        this.sunset = sunset;
        return this;
    }



    public int getPressure() {
        return pressure;
    }

    public DailyModel setPressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    public int getHumidity() {
        return humidity;
    }

    public DailyModel setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public double getDew_point() {
        return dew_point;
    }

    public DailyModel setDew_point(double dew_point) {
        this.dew_point = dew_point;
        return this;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public DailyModel setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
        return this;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public DailyModel setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
        return this;
    }



    public int getClouds() {
        return clouds;
    }

    public DailyModel setClouds(int clouds) {
        this.clouds = clouds;
        return this;
    }

    public double getPop() {
        return pop;
    }

    public DailyModel setPop(double pop) {
        this.pop = pop;
        return this;
    }

    public double getUvi() {
        return uvi;
    }

    public DailyModel setUvi(double uvi) {
        this.uvi = uvi;
        return this;
    }
}
