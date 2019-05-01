package com.example.direktoratpendidikan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

public class BeritaActivity extends AppCompatActivity {

    private SearchView cariBerita;
    public ImageView onback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cariBerita = findViewById(R.id.cari_berita);
        cariBerita.setQueryHint("Cari berita...");
        cariBerita.setIconified(false);
        //cariBerita.setOnQueryTextListener(this);
    }

}
