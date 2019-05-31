package com.example.direktoratpendidikan.mahasiswa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.BerandaFragment;
import com.example.direktoratpendidikan.R;

public class DaftarRelawanBerhasil extends AppCompatActivity {
    TextView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_relawan_berhasil);
        kembali = findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
