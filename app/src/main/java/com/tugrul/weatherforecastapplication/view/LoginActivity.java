package com.tugrul.weatherforecastapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.dialogplus.DialogPlus;
import com.tugrul.weatherforecastapplication.WeatherForecastApplication;
import com.tugrul.weatherforecastapplication.util.CommonFunctions;
import com.tugrul.weatherforecastapplication.R;

public class LoginActivity extends AppCompatActivity{



    ProgressBar progressBar;
    Handler handler;
    TextView loginButton,signUpButton,authError;
    EditText userMail,password;
    FirebaseAuth mAuth;
    LinearLayout errorLay;
    ImageView userMailIcon,passwordIcon;
    DialogPlus overlay;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =100;
    private boolean locationPermissionGranted=false;
    DialogPlus warningDialog;
    Switch modeSwitch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences userNameSharedPref;
    SharedPreferences.Editor userNameSharedPrefEditor;
    String userName="";


    boolean isDarkMode;
    LinearLayout rootLay;
    ImageView loginIv;
    Bundle bundle = new Bundle();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getLocationPermission();
         inits();
         actions();
        if(isDarkMode){
            modeSwitch.setChecked(true);
            rootLay.setBackgroundResource(R.drawable.night);
            loginIv.setImageResource(R.drawable.moon);

        }else{
            modeSwitch.setChecked(false);
            rootLay.setBackgroundResource(R.drawable.day);
            loginIv.setImageResource(R.drawable.mostly_cloudy);
        }

        if(!TextUtils.isEmpty(userName)){
            userMail.setText(userName);
        }


    }






    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        CommonFunctions.setColor(userMail,userMailIcon,LoginActivity.this,1);
        CommonFunctions.setColor(password,passwordIcon,LoginActivity.this,1);
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        }

        super.onResume();
    }
    private void inits(){


        rootLay=findViewById(R.id.rootlLay);
        sharedPreferences=getSharedPreferences("darkMode", Context.MODE_PRIVATE);
        isDarkMode=sharedPreferences.getBoolean("darkMode",false);
        sharedPrefEditor=sharedPreferences.edit();
        loginButton=findViewById(R.id.loginButtonTv);
        signUpButton=findViewById(R.id.signUpButtonTv);
        userMail=findViewById(R.id.userMailEt);
        password=findViewById(R.id.passwordEt);
        userMailIcon=findViewById(R.id.userMailIconIv);
        passwordIcon=findViewById(R.id.passwordIconIv);
        progressBar=findViewById(R.id.progressBar);
        authError=findViewById(R.id.authErrorTv);
        mAuth = FirebaseAuth.getInstance();
        errorLay=findViewById(R.id.errorLay);
        overlay=CommonFunctions.overlay(LoginActivity.this);
        handler=new Handler();
        modeSwitch=findViewById(R.id.switch1);
        loginIv=findViewById(R.id.loginIv);
        userNameSharedPref=getSharedPreferences("username", Context.MODE_PRIVATE);
        userNameSharedPrefEditor=userNameSharedPref.edit();
        userName=userNameSharedPref.getString("username","");







    }
    private void actions(){


        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(modeSwitch.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefEditor.putBoolean("darkMode",true);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefEditor.putBoolean("darkMode",false);
                }
                sharedPrefEditor.commit();
            }
        });


        userMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                CommonFunctions.setColor(userMail,userMailIcon,LoginActivity.this,1);


            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                CommonFunctions.setColor(password,passwordIcon,LoginActivity.this,1);

            }
        });

             loginButton.setOnClickListener(new View.OnClickListener() {
                 @RequiresApi(api = Build.VERSION_CODES.M)
                 @Override
                 public void onClick(View v) {
                     if(locationPermissionGranted){
                         clearfocus();
                         loginMethod();

                         Bundle bundle = new Bundle();
                         bundle.putString(FirebaseAnalytics.Param.METHOD, "loginMethod");
                         WeatherForecastApplication.getInstance().getmFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

                     }
                     else{
                         warningDialog();
                     }
                 }
             });
             signUpButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                     intent.putExtra("permission",locationPermissionGranted);
                     startActivity(intent);
                 }
             });

    }
    private void clearfocus(){
        userMail.clearFocus();
        password.clearFocus();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if(requestCode==PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
            else{

                if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                locationPermissionGranted = false;
                    warningDialog();
            }
        }
    }
private void warningDialog() {

    warningDialog = CommonFunctions.createDialog(this, R.layout.warning_dialog, Gravity.CENTER, false);
    LinearLayout twoButtonLay = (LinearLayout) warningDialog.findViewById(R.id.twoButtonLay);
    ImageView warningImage = (ImageView) warningDialog.findViewById(R.id.warningImage);
    TextView warningText = (TextView) warningDialog.findViewById(R.id.warningText);
    TextView positiveButton = (TextView) warningDialog.findViewById(R.id.positiveButtonTv);
    TextView negativeButton = (TextView) warningDialog.findViewById(R.id.negativeButtonTv);

    twoButtonLay.setVisibility(View.VISIBLE);
    warningText.setText("Uygulamayı kullanabilmeniz için konum iznini vermelisiniz.");
    positiveButton.setText("Ayarlara git");
    negativeButton.setText("İptal");
    positiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getBaseContext().getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            warningDialog.dismiss();
        }

    });

    negativeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            warningDialog.dismiss();
        }


    });

    warningDialog.show();

}





@RequiresApi(api = Build.VERSION_CODES.M)
public void loginMethod(){





    if(!TextUtils.isEmpty(userMail.getText().toString()) &&!TextUtils.isEmpty(password.getText().toString()) ){
        CommonFunctions.minimizeButton(loginButton,progressBar,LoginActivity.this);
        overlay.show();
        mAuth.signInWithEmailAndPassword(userMail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                             userNameSharedPrefEditor.putString("username",userMail.getText().toString()).commit();

                            errorLay.setVisibility(View.GONE);
                            CommonFunctions.enlargeButton(loginButton,progressBar,LoginActivity.this);
                            overlay.dismiss();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent= new Intent(LoginActivity.this, MapsActivity.class);
                                    intent.putExtra("permission",locationPermissionGranted);
                                    intent.putExtra("darkMode",isDarkMode);
                                    startActivity(intent);
                                }
                            },500);

                        } else {
                            errorLay.setVisibility(View.VISIBLE);
                            password.setText("");
                            authError.setText(task.getException().getMessage().toString());
                            overlay.dismiss();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    CommonFunctions.enlargeButton(loginButton,progressBar,LoginActivity.this);
                                }
                            },500);


                        }

                    }
                });
    }
    else{
        if(TextUtils.isEmpty(userMail.getText().toString())){
            CommonFunctions.setColor(userMail,userMailIcon,LoginActivity.this,0);

        }
        if(TextUtils.isEmpty(password.getText().toString())){
            CommonFunctions.setColor(password,passwordIcon,LoginActivity.this,0);
        }
    }




}







}