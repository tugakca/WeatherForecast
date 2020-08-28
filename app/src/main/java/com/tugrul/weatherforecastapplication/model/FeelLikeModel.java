package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

public class FeelLikeModel {


    @SerializedName("day")
    private double day;
    @SerializedName("night")
    private double night;
    @SerializedName("eve")
    private double eve;
    @SerializedName("morn")
    private double morn;


    public double getDay() {
        return day;
    }

    public FeelLikeModel setDay(double day) {
        this.day = day;
        return this;
    }

    public double getNight() {
        return night;
    }

    public FeelLikeModel setNight(double night) {
        this.night = night;
        return this;
    }

    public double getEve() {
        return eve;
    }

    public FeelLikeModel setEve(double eve) {
        this.eve = eve;
        return this;
    }

    public double getMorn() {
        return morn;
    }

    public FeelLikeModel setMorn(double morn) {
        this.morn = morn;
        return this;
    }


}

