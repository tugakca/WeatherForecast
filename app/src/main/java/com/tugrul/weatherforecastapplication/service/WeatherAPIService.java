package com.tugrul.weatherforecastapplication.service;

import com.tugrul.weatherforecastapplication.model.Weather;
import com.tugrul.weatherforecastapplication.model.WeatherDetailModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class WeatherAPIService {

    private String BASE_URL="https://api.openweathermap.org/";


     private WeatherAPI api= new Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(WeatherAPI.class);


    public Single<Weather> getUsers(double lat,double lon,String appId) {

        return api.getWeather(lat,lon,appId);
    }
}
