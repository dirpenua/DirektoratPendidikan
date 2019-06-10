package com.example.direktoratpendidikan.pengaturan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.direktoratpendidikan.R;

public class TentangDirpen extends Activity {

    public ImageView tutup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_dirpen);
        tutup = (ImageView) findViewById(R.id.close);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
