package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetailModel {

    @SerializedName("dt")
    private int  dt;
    @SerializedName("sunrise")
    private int sunrise;
    @SerializedName("sunset")
    private int sunset;
    @SerializedName("temp")
    private double temp;
    @SerializedName("feels_like")
    private double feels_like;
    @SerializedName("clouds")
    private int clouds;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("wind_speed")
    private double wind_speed;
    @SerializedName("weather")
    private List<WeatherInfoModel> weather;
    @SerializedName("dew_point")
    private double  dew_point;
    @SerializedName("uvi")
    private double  uvi;
    @SerializedName("visibility")
    private String  visibility;
    @SerializedName("wind_deg")
    private int  wind_deg;



    public List<WeatherInfoModel> getWeather() {
        return weather;
    }

    public WeatherDetailModel setWeather(List<WeatherInfoModel> weather) {
        this.weather = weather;
        return this;
    }


    public int getDt() {
        return dt;
    }

    public WeatherDetailModel setDt(int dt) {
        this.dt = dt;
        return this;
    }

    public double getTemp() {
        return temp;
    }

    public WeatherDetailModel setTemp(double temp) {
        this.temp = temp-273.15;
        return this;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public WeatherDetailModel setFeels_like(double feels_like) {
        this.feels_like = feels_like-273.15;
        return this;
    }

    public int getClouds() {
        return clouds;
    }

    public WeatherDetailModel setClouds(int clouds) {
        this.clouds = clouds;
        return this;
    }

    public int getSunrise() {
        return sunrise;
    }

    public WeatherDetailModel setSunrise(int sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public int getSunset() {
        return sunset;
    }

    public WeatherDetailModel setSunset(int sunset) {
        this.sunset = sunset;
        return this;
    }

    public int getHumidity() {
        return humidity;
    }

    public WeatherDetailModel setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public int getPressure() {
        return pressure;
    }

    public WeatherDetailModel setPressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public WeatherDetailModel setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
        return this;
    }



    public double getDew_point() {
        return dew_point;
    }

    public WeatherDetailModel setDew_point(double dew_point) {
        this.dew_point = dew_point;
        return this;
    }

    public double getUvi() {
        return uvi;
    }

    public WeatherDetailModel setUvi(double uvi) {
        this.uvi = uvi;
        return this;
    }

    public String getVisibility() {
        return visibility;
    }

    public WeatherDetailModel setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public WeatherDetailModel setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
        return this;
    }
}
