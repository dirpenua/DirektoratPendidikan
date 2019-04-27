package com.example.direktoratpendidikan.pengaturan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.direktoratpendidikan.R;

public class ProfilActivity extends AppCompatActivity {

    public ImageView tutup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tutup = (ImageView) findViewById(R.id.edit_close);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
