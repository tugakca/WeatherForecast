package com.tugrul.weatherforecastapplication.view;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tugrul.weatherforecastapplication.R;
import com.tugrul.weatherforecastapplication.adapter.WeatherAdapter;
import com.tugrul.weatherforecastapplication.databinding.FragmentWeatherBottomSheetBinding;
import com.tugrul.weatherforecastapplication.model.DailyModel;
import com.tugrul.weatherforecastapplication.model.Weather;
import com.tugrul.weatherforecastapplication.viewmodel.WeatherViewModel;
import java.util.ArrayList;



public class WeatherBottomSheetFragment extends BottomSheetDialogFragment {

    LinearLayout errorLay,childLay,loadingLay;
    ProgressBar progressBar;
    WeatherViewModel viewModel;
    RecyclerView weatherRv;
    WeatherAdapter weatherAdapter;
    LinearLayoutManager linearLayoutManager;
    String locationName;
    LatLng location;
    String APP_ID="d213682a46f3684ccaa1e381048bb731";
    TextView currentLocation;


    public WeatherBottomSheetFragment(String locationName, LatLng location) {
        this.locationName=locationName;
        this.location=location;

    }



    public static WeatherBottomSheetFragment newInstance(String locationName, LatLng location) {
        WeatherBottomSheetFragment fragment = new WeatherBottomSheetFragment(locationName,location);
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWeatherBottomSheetBinding binding=DataBindingUtil.inflate(inflater, R.layout.fragment_weather_bottom_sheet, container, false);
        View view = binding.getRoot();
        viewModel= ViewModelProviders.of(this).get(WeatherViewModel.class);
        viewModel.refreshData(location.latitude,location.longitude,APP_ID);
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inits(view);



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inits(View view){
        linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        weatherRv=view.findViewById(R.id.futureWeatherRv);
        weatherAdapter=new WeatherAdapter();
        weatherRv.setLayoutManager(linearLayoutManager);
        weatherRv.setAdapter(weatherAdapter);
        errorLay=view.findViewById(R.id.errorLay);
        progressBar=view.findViewById(R.id.progressBar);
        loadingLay=view.findViewById(R.id.loadingLay);
        childLay=view.findViewById(R.id.childLay);
        currentLocation=view.findViewById(R.id.currentLocationTv);
        observeWeatherData();


    }

    private void observeWeatherData(){

        viewModel.weatherData.observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {

                if(weather.getDaily()!=null){
                    ArrayList<DailyModel>tempDailyList = new ArrayList<>();
                    tempDailyList.addAll(weather.getDaily());
                    tempDailyList.remove(0);
                    weatherAdapter.addDailyWeatherList(tempDailyList);
                    currentLocation.setText(locationName);
                }
            }
        });

        viewModel.error.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    errorLay.setVisibility(View.VISIBLE);
                    childLay.setVisibility(View.GONE);
                }else{
                    childLay.setVisibility(View.VISIBLE);
                    errorLay.setVisibility(View.GONE);
                }
            }
        });
        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    if (aBoolean){
                        loadingLay.setVisibility(View.VISIBLE);
                        childLay.setVisibility(View.GONE);
                    }else{
                        loadingLay.setVisibility(View.GONE);
                        childLay.setVisibility(View.VISIBLE);

                    }

            }


        });





    }


}