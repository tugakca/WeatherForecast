package com.tugrul.weatherforecastapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tugrul.weatherforecastapplication.R;
import com.tugrul.weatherforecastapplication.databinding.ItemWeatherBinding;
import com.tugrul.weatherforecastapplication.model.DailyModel;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {


    private List<DailyModel> dailyWeatherList=new ArrayList<>();
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemWeatherBinding binding=   DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_weather, parent, false);
        return new WeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {


        DailyModel daily=dailyWeatherList.get(position);
        holder.itemWeatherBinding.setModel(daily);

    }

    @Override
    public int getItemCount() {
        if (dailyWeatherList != null) {
            return dailyWeatherList.size();
        } else {
            return 0;
        }
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        ItemWeatherBinding itemWeatherBinding;

        public WeatherViewHolder(@NonNull ItemWeatherBinding itemWeatherBinding) {
            super(itemWeatherBinding.getRoot());
            this.itemWeatherBinding=itemWeatherBinding;
        }
    }



    public void addDailyWeatherList(List<DailyModel> model) {
      dailyWeatherList.addAll(model);
        notifyDataSetChanged();
    }
}
