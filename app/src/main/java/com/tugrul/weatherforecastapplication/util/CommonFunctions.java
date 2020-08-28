package com.tugrul.weatherforecastapplication.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.tugrul.weatherforecastapplication.R;
import com.tugrul.weatherforecastapplication.WeatherForecastApplication;
import com.tugrul.weatherforecastapplication.model.DailyModel;
import com.tugrul.weatherforecastapplication.model.WeatherInfoModel;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class CommonFunctions {




    @BindingAdapter("android:tempString")
    public static void tempToString(TextView view,double temp){

        int tempInt=(int)(temp-273.15);
        view.setText(String.valueOf(tempInt));

    }


    @BindingAdapter("android:humString")
    public static void humToString(TextView view,int hum){
        view.setText(String.valueOf("%" +hum));
    }


    @BindingAdapter("android:currentDay")
    public static void currentDay(TextView view,int time){
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        java.util.Date currenTimeZone=new java.util.Date((long)time*1000);
        view.setText( new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currenTimeZone.getTime()));
    }


    @BindingAdapter("android:time")
    public static void timeToString(TextView view,int time){

        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        java.util.Date currenTimeZone=new java.util.Date((long)time*1000);

        String date="";

        if(currenTimeZone.getHours()<10){
            date=date+"0"+currenTimeZone.getHours();
        }
        else{
            date=date+currenTimeZone.getHours();
        }

        if(currenTimeZone.getMinutes()<10){
            date=date+":"+"0"+currenTimeZone.getMinutes();
        }
        else{
            date=date+":"+currenTimeZone.getMinutes();
        }

        view.setText(date);

    }



    @BindingAdapter("android:cityName")
    public static void getCityName(TextView view,String name){
       if(name!=null){
           view.setText(name.split("/")[1]);
       }
    }



    @BindingAdapter("android:itemDay")
    public static void getItemDay(TextView view, int dailyModel){


        if(dailyModel!=0){
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            java.util.Date currenTimeZone=new java.util.Date((long) dailyModel*1000);
            view.setText( new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currenTimeZone.getTime()));

        }

    }

    @BindingAdapter("android:backgroundImage")
    public static void backgroundImage(LinearLayout linearLayout, List<WeatherInfoModel>weatherInfoModel){
        if(weatherInfoModel!=null &&weatherInfoModel.get(0).getId()!=0 &&weatherInfoModel.get(0).getIcon()!=null) {
            if (weatherInfoModel.get(0).getIcon().contains("n")) {
                linearLayout.setBackgroundResource(R.drawable.night);
            } else {
                linearLayout.setBackgroundResource(R.drawable.day);
            }
        }
    }


    @BindingAdapter("android:weatherImage")
    public static void weatherImage(ImageView view, List<WeatherInfoModel>weatherInfoModel){
        if(weatherInfoModel!=null &&weatherInfoModel.get(0).getId()!=0 &&weatherInfoModel.get(0).getIcon()!=null){
            switch (weatherInfoModel.get(0).getIcon()){
                case  "01d":view.setImageResource(R.drawable.sunny); return;
                case  "01n":view.setImageResource(R.drawable.moon); return;
                case  "02d":view.setImageResource(R.drawable.cloudy); return;
                case  "02n":view.setImageResource(R.drawable.night_cloudy); return;
                case  "03d":view.setImageResource(R.drawable.mostly_cloudy); return;
                case  "03n":view.setImageResource(R.drawable.night_cloudy); return;
                case  "04d":view.setImageResource(R.drawable.mostly_cloudy); return;
                case  "04n":view.setImageResource(R.drawable.night_cloudy); return;
                case  "09d":view.setImageResource(R.drawable.drizzle); return;
                case  "09n":view.setImageResource(R.drawable.night_drizzle); return;
                case  "10d":view.setImageResource(R.drawable.drizzle); return;
                case  "10n":view.setImageResource(R.drawable.night_drizzle); return;
                case  "11d":view.setImageResource(R.drawable.thunderstorms); return;
                case  "11n":view.setImageResource(R.drawable.night_thunderstorms); return;
                case  "13d":view.setImageResource(R.drawable.snow); return;
                case  "13n":view.setImageResource(R.drawable.snow); return;
                case  "50d":view.setImageResource(R.drawable.haze); return;
                case  "50n":view.setImageResource(R.drawable.haze); return;
                default:view.setImageResource(R.drawable.sunny); return;
            }





        }
    }



    @BindingAdapter("android:bottomSheet")
    public static void bottomSheetSettings(LinearLayout layout, boolean isActive){

//               if(isActive){
//
//                   BottomSheetBehavior bottomSheetBot = BottomSheetBehavior.from(layout);
//
//                   bottomSheetBot.setHideable(false);
//
//               }


    }





    public static int dpToPx(int dp, Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static  void minimizeButton(TextView view, ProgressBar progressBar,Context context) {
        view.setText("");
        animate(view, 0, 300,context);
        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

    }

    public static  void enlargeButton(final TextView view, final ProgressBar progressBar, Context context){
          Handler handler=new Handler();
        view.setVisibility(View.VISIBLE);
        animate(view, dpToPx(300,context), 300,context);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setText("LOGIN");
                progressBar.setVisibility(View.GONE);
            }
        }, 300);
    }

    public static void animate(final View v, int END_WIDTH, int DURATION, final Context context) {
        ValueAnimator anim = ValueAnimator.ofInt(v.getMeasuredWidth(), END_WIDTH);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                if (val > CommonFunctions.dpToPx(50,context)) {
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.width = val;
                    v.setLayoutParams(layoutParams);
                } else v.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(DURATION);
        anim.start();
    }


    public static DialogPlus createDialog(Context context, @LayoutRes int layout, int gravity, boolean cancelable) {


        return DialogPlus.newDialog(context).setContentHolder(new ViewHolder(layout))
                .setGravity(gravity).setCancelable(false).create();


    }
    public static DialogPlus overlay(Context context) {
        return DialogPlus.newDialog(context).setContentHolder(new ViewHolder(R.layout.overlay))
                .setCancelable(false).create();
    }






    public static DialogPlus createDialogWithListener(Context context, @LayoutRes int layout, int gravity,
                                                      boolean cancelable, OnDismissListener listener) {
        DialogPlusBuilder dialogPlusBuilder = DialogPlus.newDialog(context).setContentHolder(new ViewHolder(layout))
                .setGravity(gravity).setCancelable(false);
        if (listener != null) {
            dialogPlusBuilder.setOnDismissListener(listener);

        }
        return dialogPlusBuilder
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setColor(EditText editText, ImageView icon, Context context,int RESULT_CODE){



        if(RESULT_CODE==1){
            icon.setImageTintList(context.getColorStateList(R.color.text_color));
            editText.setBackgroundTintList(context.getColorStateList(R.color.text_color));
            editText.setHintTextColor(context.getColor(R.color.text_color));
        }
        else if(RESULT_CODE==0){
            icon.setImageTintList(context.getColorStateList(R.color.red_color));
            editText.setBackgroundTintList(context.getColorStateList(R.color.red_color));
            editText.setHintTextColor(context.getColor(R.color.red_color));

        }

    }

}