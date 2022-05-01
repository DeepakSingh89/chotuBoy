package com.chotupartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.chotupartner.R;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler();
        a = 'A' - '9';
        Log.d(TAG, "onCreate: " + a);
    }

    public void handler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ChotuBoyPrefs.getBoolean(SplashActivity.this, Constants.LoginCheck)) {
                    Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginThroughMobileNumberActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 5000);
    }
}
