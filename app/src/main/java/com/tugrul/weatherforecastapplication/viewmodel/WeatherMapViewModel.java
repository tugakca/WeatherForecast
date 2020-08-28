package com.tugrul.weatherforecastapplication.viewmodel;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tugrul.weatherforecastapplication.service.WeatherMapAPIService;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class WeatherMapViewModel extends ViewModel {



    public MutableLiveData<ResponseBody> weatherLayer=new MutableLiveData<>();
    public MutableLiveData<Boolean> loading=new MutableLiveData<>();
    private WeatherMapAPIService weatherAPIService=new WeatherMapAPIService();
    private CompositeDisposable disposable=new CompositeDisposable();


    public void refresh(int zoom,int lat, int lon){

        getDataFromAPI(zoom,lon,lat);


    }

    public void getDataFromAPI(int zoom,int lat, int lon){



        disposable.add(

                weatherAPIService.getWeatherLayer(zoom,lon,lat)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ResponseBody>() {
                            @Override
                            public void onSuccess(ResponseBody weatherMapModel) {
                                weatherLayer.setValue(weatherMapModel);
                                loading.setValue(false);
                            }


                            @Override
                            public void onError(Throwable e) {
                                 loading.setValue(true);

                            }
                        })
        );






    }
}
