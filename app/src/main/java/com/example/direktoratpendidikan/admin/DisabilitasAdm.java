package com.example.direktoratpendidikan.admin;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.adapter.TabAdapter;

public class DisabilitasAdm extends AppCompatActivity {

    ImageView tutup;
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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabTambahPeserta(), "MBK");
        adapter.addFragment(new TabPesertaDitambahkan(), "Relawan");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
