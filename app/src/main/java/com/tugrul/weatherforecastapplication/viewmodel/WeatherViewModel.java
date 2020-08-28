package com.tugrul.weatherforecastapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tugrul.weatherforecastapplication.model.Weather;

import com.tugrul.weatherforecastapplication.service.WeatherAPIService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {


    public MutableLiveData<Weather> weatherData=new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =new MutableLiveData();
    public MutableLiveData<Boolean> error=new MutableLiveData<>();
    private WeatherAPIService weatherAPIService=new WeatherAPIService();
    private CompositeDisposable disposable=new CompositeDisposable();
    public MutableLiveData<Boolean> bottomSheetBehaviorState = new MutableLiveData();

    public void refreshData(double lat,double lon,String appId) {

        getDataFromAPI(lat,lon,appId);

    }

    public void getDataFromAPI(double lat,double lon,String appId){

        loading.setValue(true);

        disposable.add(

                weatherAPIService.getUsers(lat,lon,appId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Weather>(


                ) {
                    @Override
                    public void onSuccess(Weather weatherModels) {

                        weatherData.setValue(weatherModels);
                        loading.setValue(false);
                        error.setValue(false);
                        bottomSheetBehaviorState.setValue(true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        error.setValue(true);
                        loading.setValue(true);
                        bottomSheetBehaviorState.setValue(false);
                        e.printStackTrace();

                    }
                })
        );






    }









}
