package com.example.direktoratpendidikan.mahasiswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;

public class SplashDisabilitas extends AppCompatActivity {

    TextView gabung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_disabilitas);
        gabung = findViewById(R.id.gabung);
        gabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disabilitas = new Intent(getApplicationContext(), DisabilitasMhs.class);
                startActivity(disabilitas);
            }
        });
    }
}
