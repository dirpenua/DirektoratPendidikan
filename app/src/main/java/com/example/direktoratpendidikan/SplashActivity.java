package com.example.direktoratpendidikan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
//        session = new SessionManager(getApplicationContext());
//
//        /**
//         * Call this function whenever you want to check user login
//         * This will redirect user to LoginActivity is he is not
//         * logged in
//         * */
//        session.checkLogin();

        new Handler().postDelayed(new Runnable() {
            // langsung pindah ke MainActivity setelah splashscreen

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}