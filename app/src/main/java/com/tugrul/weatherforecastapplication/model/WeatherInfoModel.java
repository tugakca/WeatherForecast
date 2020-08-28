package com.tugrul.weatherforecastapplication.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfoModel {




    @SerializedName("id")
    private int id;
    @SerializedName("clear")
    private String clear;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

    public int getId() {
        return id;
    }

    public WeatherInfoModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getClear() {
        return clear;
    }

    public WeatherInfoModel setClear(String clear) {
        this.clear = clear;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WeatherInfoModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public WeatherInfoModel setIcon(String icon) {
        this.icon = icon;
        return this;
    }
}
