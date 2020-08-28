package com.tugrul.weatherforecastapplication.service;

import com.tugrul.weatherforecastapplication.model.Weather;


import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherMapAPI {

    @Headers({"Accept: application/json"})
    @GET("/map/temp_new/{z}/{x}/{y}.png?appid=3cf73cc0869def1e4e908afef8fb630b")
    Single<ResponseBody> getWeatherMapLayer(@Path("z") int zoomLevel, @Path("x") int latitude, @Path("y") int longitude);

}
