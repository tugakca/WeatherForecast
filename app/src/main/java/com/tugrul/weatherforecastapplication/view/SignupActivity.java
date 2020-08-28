package com.tugrul.weatherforecastapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.List;


public class SignupActivity extends AppCompatActivity{


    EditText userName,userMail,password,confirmPassword;
    TextView signUpButton,authError;
    ImageView userMailIcon,userNameIcon,passwordIcon,confirmPasswordIcon;
    FirebaseAuth mAuth;
    LinearLayout errorLay;
    Handler handler;
    ProgressBar progressBar;
    DialogPlus overlay;
    boolean isDarkMode;
    LinearLayout rootLay;
    ImageView signUpIcon;
    boolean locationPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inits();
        actions();

    }


    private void inits(){
        locationPermissionGranted=getIntent().getBooleanExtra("darkMode",false);
        isDarkMode=getIntent().getBooleanExtra("darkMode",false);
        userName=findViewById(R.id.nameEt);
        userMail=findViewById(R.id.emailEt);
        password=findViewById(R.id.passwordEt);
        confirmPassword=findViewById(R.id.confirmPasswordEt);
        userMailIcon=findViewById(R.id.emailIconIv);
        userNameIcon=findViewById(R.id.nameIconIv);
        passwordIcon=findViewById(R.id.passwordIconIv);
        confirmPasswordIcon=findViewById(R.id.confirmPasswordIconIv);
        signUpButton=findViewById(R.id.signUpButtonTv);
        errorLay=findViewById(R.id.errorLay);
        authError=findViewById(R.id.authErrorTv);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        rootLay=findViewById(R.id.rootlLay);
        overlay= CommonFunctions.overlay(this);
        signUpIcon=findViewById(R.id.signUpIv);
        handler=new Handler();


        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            rootLay.setBackgroundResource(R.drawable.night);
            signUpIcon.setImageResource(R.drawable.moon);
        }else{
            rootLay.setBackgroundResource(R.drawable.day);
            signUpIcon.setImageResource(R.drawable.mostly_cloudy);
        }




    }


    @Override
    protected void onResume() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        }


        super.onResume();


    }

    private void actions(){

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CommonFunctions.setColor(userName,userNameIcon,SignupActivity.this,1);
            }
        });
        userMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CommonFunctions.setColor(userMail,userMailIcon,SignupActivity.this,1);
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CommonFunctions.setColor(password,passwordIcon,SignupActivity.this,1);
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CommonFunctions.setColor(confirmPassword,confirmPasswordIcon,SignupActivity.this,1);

            }
        });






        signUpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                clearfocus();
                signUp();

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "signUpMethod");
                WeatherForecastApplication.getInstance().getmFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);



            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void signUp(){

        if(!TextUtils.isEmpty(userName.getText().toString())  &&!TextUtils.isEmpty(userMail.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(confirmPassword.getText().toString()) ){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                errorLay.setVisibility(View.GONE);
                overlay.show();
                CommonFunctions.minimizeButton(signUpButton,progressBar,SignupActivity.this);
                mAuth.createUserWithEmailAndPassword(userMail.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    overlay.dismiss();
                                    errorLay.setVisibility(View.GONE);

                                    Intent intent=new Intent(SignupActivity.this, MapsActivity.class);
                                    intent.putExtra("permission",locationPermissionGranted);
                                    startActivity(intent);

                                    //INTENT
                                }
                                else{
                                    authError.setText(task.getException().getMessage());
                                    errorLay.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            overlay.dismiss();
                                            CommonFunctions.enlargeButton(signUpButton,progressBar,SignupActivity.this);
                                            confirmPassword.setText("");
                                            password.setText("");

                                        }
                                    },100);
                                }
                            }
                        });

            }
            else{
                errorLay.setVisibility(View.VISIBLE);
                authError.setText("The passwords you have entered do not match.");
                password.setText("");
                confirmPassword.setText("");
                CommonFunctions.setColor(password,passwordIcon,SignupActivity.this,0);
                CommonFunctions.setColor(confirmPassword,confirmPasswordIcon,SignupActivity.this,0);
            }
        }
        else {
            if (TextUtils.isEmpty(userName.getText().toString())) {
                CommonFunctions.setColor(userName, userNameIcon, SignupActivity.this, 0);
            }
            if (TextUtils.isEmpty(userMail.getText().toString())) {
                CommonFunctions.setColor(userMail, userMailIcon, SignupActivity.this, 0);
            }
            if (TextUtils.isEmpty(password.getText().toString())) {
                CommonFunctions.setColor(password, passwordIcon, SignupActivity.this, 0);
                password.setText("");
                confirmPassword.setText("");
            }
            if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                CommonFunctions.setColor(confirmPassword, confirmPasswordIcon, SignupActivity.this, 0);
                password.setText("");
                confirmPassword.setText("");
            }
        }



    }

    private void clearfocus(){

        userName.clearFocus();
        userMail.clearFocus();
        password.clearFocus();
        confirmPassword.clearFocus();

    }

}