package com.example.direktoratpendidikan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.api.ApiClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class KalenderAkademik extends AppCompatActivity {

    public ImageView onback, gambarkalender;
    public TextView klikdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_akademik);
        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gambarkalender = findViewById(R.id.kalenderakademik);
        klikdetail = findViewById(R.id.klikdetail);


        final String gambar = "KL01.jpg";
        Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL+gambar).into(gambarkalender, new Callback() {
            @Override
            public void onSuccess() {
                gambarkalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = ApiClient.IMAGE_URL+gambar;
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onError() {
                gambarkalender.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Halaman tidak ditemukan. Harap hubungi admin melalui menu BANTUAN",Toast.LENGTH_SHORT);
            }
        });

        klikdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ApiClient.IMAGE_URL+gambar;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });

    }
}
