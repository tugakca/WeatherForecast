package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {


    private Weather weather;

    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_offset")
    private int timezone_offset;
    @SerializedName("current")
    private WeatherDetailModel current;
    @SerializedName("minutely")
    private List<MinuteModel>minutely;
    @SerializedName("hourly")
    private List<HourlyModel>hourly;
    @SerializedName("daily")
    private List<DailyModel>daily;


    public List<DailyModel> getDaily() {
        return daily;
    }

    public Weather setDaily(List<DailyModel> daily) {
        this.daily = daily;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Weather setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Weather setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Weather setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public Weather setTimezone_offset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
        return this;
    }

    public WeatherDetailModel getCurrent() {
        return current;
    }

    public Weather setCurrent(WeatherDetailModel current) {
        this.current = current;
        return this;
    }

    public List<MinuteModel> getMinutely() {
        return minutely;
    }

    public Weather setMinutely(List<MinuteModel> minutely) {
        this.minutely = minutely;
        return this;
    }

    public List<HourlyModel> getHourly() {
        return hourly;
    }

    public Weather setHourly(List<HourlyModel> hourly) {
        this.hourly = hourly;
        return this;
    }


}
