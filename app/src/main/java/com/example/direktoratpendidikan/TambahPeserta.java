package com.example.direktoratpendidikan;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.direktoratpendidikan.adapter.TabAdapter;
import com.example.direktoratpendidikan.admin.TabDosen;
import com.example.direktoratpendidikan.admin.TabMahasiswa;
import com.example.direktoratpendidikan.admin.TabPesertaDitambahkan;
import com.example.direktoratpendidikan.admin.TabTambahPeserta;

public class TambahPeserta extends AppCompatActivity {

    ImageView tutup;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

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
        adapter.addFragment(new TabTambahPeserta(), "Tambah Peserta");
        adapter.addFragment(new TabPesertaDitambahkan(), "Peserta yang Ada");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
