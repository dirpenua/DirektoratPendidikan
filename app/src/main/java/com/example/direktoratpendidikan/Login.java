package com.example.direktoratpendidikan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.direktoratpendidikan.R;

public class Login extends AppCompatActivity {

    Button masuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        masuk = (Button) findViewById(R.id.btn_login);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UbahPWUserBaru.class);
                startActivity(i);
            }
        });

    }
}
