package com.tugrul.weatherforecastapplication.service;

import com.tugrul.weatherforecastapplication.model.Weather;
import com.tugrul.weatherforecastapplication.model.WeatherDetailModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {


    @GET("/data/2.5/onecall?")
    Single<Weather> getWeather(@Query("lat") double latitute,@Query("lon") double longitude,@Query("appid") String appId);

}
