package com.tugrul.weatherforecastapplication.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherMapAPIService {



    private String BASE_URL="https://tile.openweathermap.org/";
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private WeatherMapAPI api= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WeatherMapAPI.class);



//    https://tile.openweathermap.org/map/temp_new/10/34/29.png?appid=3cf73cc0869def1e4e908afef8fb630b
    public Single<ResponseBody> getWeatherLayer(int zoom,int lat, int lon) {

        return api.getWeatherMapLayer(zoom,lat,lon);
    }
}
