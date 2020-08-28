package com.tugrul.weatherforecastapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class WeatherForecastApplication extends MultiDexApplication {
    private static FirebaseAnalytics mFirebaseAnalytics;

    private static WeatherForecastApplication weatherForecastApplication;




   public static  WeatherForecastApplication getInstance(){
       if (weatherForecastApplication == null) {
           weatherForecastApplication = new WeatherForecastApplication();
       }
       return weatherForecastApplication;


   }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public FirebaseAnalytics getmFirebaseAnalytics() {

        return mFirebaseAnalytics;
    }





}
