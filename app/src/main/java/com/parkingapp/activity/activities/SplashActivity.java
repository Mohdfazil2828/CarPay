package com.parkingapp.activity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.parkingapp.R;
import com.parkingapp.activity.utils.Utils;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    //After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("value", "" + Utils.getAdminId(getApplicationContext()));
                Log.e("value", "" + Utils.getUserId(getApplicationContext()));

                if (Utils.getIsLoginAdmin(SplashActivity.this).equals("0") && Utils.getIsLoginUser(SplashActivity.this).equals("0")) {
                    Intent i = new Intent(SplashActivity.this, OptionActivity.class);
                    startActivity(i);
                    finish();
                } else if (Utils.getIsLoginAdmin(SplashActivity.this).equals("1")) {
                    Intent i = new Intent(SplashActivity.this, AdminDashboardActivity.class);
                    startActivity(i);
                    finish();
                } else if (Utils.getIsLoginUser(SplashActivity.this).equals("1")) {
                    if (Utils.getIsLoggedIn(SplashActivity.this).equals("1")) {
                        Intent i = new Intent(SplashActivity.this, UserTimeStart.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashActivity.this, UserDashboardActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        }, 3000);

    }
}
