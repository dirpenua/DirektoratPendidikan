package com.example.direktoratpendidikan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

//    SessionManager session;

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        session = new SessionManager(getApplicationContext());
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences,Context.MODE_PRIVATE);
        nama = getIntent().getStringExtra(TAG_NAMA);
        nipnik = getIntent().getStringExtra(TAG_NIPNIK);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
//        session.checkLogin();
//        // get user data from session
//        HashMap<String, String> user = session.getUserDetails();
//
//        // name
//        String nama = user.get(SessionManager.KEY_NAMA);
//
//        // email
//        String nipnik = user.get(SessionManager.KEY_NIPNIK);

        // kita set default nya Home Fragment
        loadFragment(new BerandaFragment());

        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    // method untuk load fragment yang sesuai
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    // method listener untuk logika pemilihan
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.beranda:
                fragment = new BerandaFragment();
                break;
            case R.id.prosedur:
                fragment = new ProsedurFragment();
                break;
            case R.id.agenda:
                fragment = new AgendaFragment();
                break;
            case R.id.beasiswa:
                fragment = new BeasiswaFragment();
                break;
            case R.id.akun:
                fragment = new AkunFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
