package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

public class TempModel {


    @SerializedName("day")
    private double day;
    @SerializedName("min")
    private double min;
    @SerializedName("max")
    private double max;
    @SerializedName("night")
    private double night;
    @SerializedName("eve")
    private double eve;
    @SerializedName("morn")
    private double morn;


    public double getDay() {
        return day;
    }

    public TempModel setDay(double day) {
        this.day = day-273.15;
        return this;
    }

    public double getMin() {
        return min;
    }

    public TempModel setMin(double min) {
        this.min = min;
        return this;
    }

    public double getMax() {
        return max;
    }

    public TempModel setMax(double max) {
        this.max = max;
        return this;
    }

    public double getNight() {
        return night;
    }

    public TempModel setNight(double night) {
        this.night = night-273.15;
        return this;
    }

    public double getEve() {
        return eve;
    }

    public TempModel setEve(double eve) {
        this.eve = eve-273.15;
        return this;
    }

    public double getMorn() {
        return morn;
    }

    public TempModel setMorn(double morn) {
        this.morn = morn-273.15;
        return this;
    }
}