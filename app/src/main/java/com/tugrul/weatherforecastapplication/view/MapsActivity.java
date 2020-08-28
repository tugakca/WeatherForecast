package com.tugrul.weatherforecastapplication.view;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.dialogplus.DialogPlus;
import com.tugrul.weatherforecastapplication.R;
import com.tugrul.weatherforecastapplication.WeatherForecastApplication;
import com.tugrul.weatherforecastapplication.util.CommonFunctions;
import com.tugrul.weatherforecastapplication.viewmodel.WeatherMapViewModel;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    boolean locationPermissionGranted;
    ImageView button;
    LatLng placeLocation;
    ImageView logOutIv;
    DialogPlus warningDialog;
    androidx.appcompat.widget.SearchView  searchView;
    WeatherBottomSheetFragment weatherBottomSheetFragment;
    FirebaseAuth mAuth;
    WeatherMapViewModel weatherMapViewModel;
    GroundOverlayOptions newarkMap=null;
    GroundOverlay imageOverlay = null;
    boolean isDarkMode;
    RelativeLayout buttonLay;
    LinearLayout searchLay;
    Geocoder geocoder;
    Marker previousMarker;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inits(){
        logOutIv=findViewById(R.id.logOutIv);
        button=findViewById(R.id.button);
        searchView=findViewById(R.id.searchView);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
        locationPermissionGranted=getIntent().getBooleanExtra("permission",false);
        mAuth=FirebaseAuth.getInstance();
        weatherMapViewModel= ViewModelProviders.of(this).get(WeatherMapViewModel.class);
         geocoder= new Geocoder(MapsActivity.this);
         buttonLay=findViewById(R.id.buttonLay);
         searchLay=findViewById(R.id.searchLay);
         sharedPrefSettings();



    }

    private void actions(){

        logOutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser()!=null && isNetworkConnected()){
                    logoutDialog();
                }

            }
        });
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!=null && !TextUtils.isEmpty(query.toString())){
                    List<Address> addressList=null;

                    try{
                        addressList= geocoder.getFromLocationName(query,1);
                        if(addressList.size()!=0){
                            placeLocation=new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());

                            if(previousMarker!=null){
                                previousMarker.remove();
                            }
                            if(imageOverlay!=null){
                                imageOverlay.remove();
                                setMapLayer();
                            }

                           previousMarker= mMap.addMarker(new MarkerOptions().position(placeLocation).title(query));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation,15));
                            clearSearchView();
                            if( placeLocation!=null &&addressList!=null){
                                setMapLayer();
                                weatherBottomSheetFragment=new WeatherBottomSheetFragment(addressList.get(0).getAdminArea(),placeLocation);
                                weatherBottomSheetFragment.show(getSupportFragmentManager(),"TAG");
                            }
                        }else{
                            warningDialog();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeLocation!=null){
                    List<Address>addressList=null;
                    try {
                        addressList=geocoder.getFromLocation(placeLocation.latitude,placeLocation.longitude,1);
                        if(addressList!=null){
                            weatherBottomSheetFragment = new WeatherBottomSheetFragment(addressList.get(0).getAdminArea(),placeLocation);
                            weatherBottomSheetFragment.show(getSupportFragmentManager(),"TAG");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        logoutDialog();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        isDarkMode=(boolean)getIntent().getBooleanExtra("darkMode",false);
        inits();
        getDeviceLocation();
        actions();







    }
    private void clearSearchView(){
        if(searchView!=null){
            searchView.setQuery("", false);
            searchView.clearFocus();
        }
    }
    private void warningDialog(){
        warningDialog=  CommonFunctions.createDialog(MapsActivity.this,R.layout.warning_dialog, Gravity.CENTER,false);
        TextView warningText = (TextView) warningDialog.findViewById(R.id.warningText);
        LinearLayout singleLayout=(LinearLayout) warningDialog.findViewById(R.id.singleButtonLay);
        TextView singleButtonTv=(TextView) warningDialog.findViewById(R.id.singleButtonTv);
        singleLayout.setVisibility(View.VISIBLE);
        warningText.setText("Searching is not found!");
        singleButtonTv.setText("Okay");
        singleButtonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchView();
                warningDialog.dismiss();
            }
        });
        warningDialog.show();
    }
    private void logoutDialog(){
        warningDialog=  CommonFunctions.createDialog(MapsActivity.this,R.layout.warning_dialog, Gravity.CENTER,false);
        TextView warningText = (TextView) warningDialog.findViewById(R.id.warningText);
        LinearLayout twoButtonLay=(LinearLayout) warningDialog.findViewById(R.id.twoButtonLay);
        TextView positiveButtonTv=(TextView) warningDialog.findViewById(R.id.positiveButtonTv);
        TextView negativeButtonTv=(TextView) warningDialog.findViewById(R.id.negativeButtonTv);
        twoButtonLay.setVisibility(View.VISIBLE);
        warningText.setText("Are you sure you want to exit?");
        positiveButtonTv.setText("Cancel");
        negativeButtonTv.setText("Exit");


        positiveButtonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchView();
                warningDialog.dismiss();
            }
        });

      negativeButtonTv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mAuth.signOut();
              warningDialog.dismiss();
              Intent intent =new Intent(MapsActivity.this,LoginActivity.class);
              startActivity(intent);
          }
      });
        warningDialog.show();
    }
    private void setMapLayer(){
                int zoomLevel =(int)mMap.getCameraPosition().zoom;
                weatherMapViewModel.refresh(zoomLevel,(int)placeLocation.latitude,(int)placeLocation.longitude);
                weatherMapViewModel.weatherLayer.observe(MapsActivity.this, new Observer<ResponseBody>() {
                    @Override
                    public void onChanged(ResponseBody responseBody) {
                        if(responseBody!=null) {
                            Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                              if(bmp!=null){
                                  newarkMap =new GroundOverlayOptions()
                                          .image(BitmapDescriptorFactory.fromBitmap(bmp))
                                          .position(placeLocation, 8600f, 6500f);
                                  imageOverlay = mMap.addGroundOverlay(newarkMap);
                              }
                        }
                    }
                });


    }
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                placeLocation=new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                if(previousMarker!=null){
                                    previousMarker.remove();
                                }
                                if(imageOverlay!=null){
                                    imageOverlay.remove();
                                    setMapLayer();
                                }

                              previousMarker=  mMap.addMarker(new MarkerOptions().position(placeLocation));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 15));
                               setMapLayer();
                               List<Address>addressList=null;
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.METHOD, "getDeviceLocation");
                                WeatherForecastApplication.getInstance().getmFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);

                                try {
                                    addressList= geocoder.getFromLocation(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude(),1);
                                    if(addressList!=null){
                                        weatherBottomSheetFragment = new WeatherBottomSheetFragment(addressList.get(0).getAdminArea(),placeLocation);
                                        weatherBottomSheetFragment.show(getSupportFragmentManager(),"TAG");
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {

                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {


            mMap = googleMap;
            mMap.setOnMapClickListener(MapsActivity.this);



    }




    @Override
    public void onMapClick(LatLng latLng) {
       clearSearchView();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sharedPrefSettings(){
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            button.setImageResource(R.drawable.moon);
            searchLay.setBackgroundColor(Color.parseColor("#09275c"));
            buttonLay.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#09275c")));

        }else {
            button.setImageResource(R.drawable.mostly_cloudy);
            searchLay.setBackgroundColor(Color.parseColor("#4698E1"));
            buttonLay.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4698E1")));
        }

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }



}