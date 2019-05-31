package com.example.direktoratpendidikan.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.adapter.TabAdapter;
import com.example.direktoratpendidikan.mahasiswa.SplashDisabilitas;

public class DisabilitasAdm extends AppCompatActivity {

    ImageView tutup, permintaan;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabilitas_adm);

        tutup = (ImageView) findViewById(R.id.kembali);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        permintaan = (ImageView) findViewById(R.id.relawanrequest);
        permintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent permintaan = new Intent(getApplicationContext(), PermintaanRelawan.class);
                startActivity(permintaan);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabMBK(), "MBK");
        adapter.addFragment(new TabRelawan(), "Relawan");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
