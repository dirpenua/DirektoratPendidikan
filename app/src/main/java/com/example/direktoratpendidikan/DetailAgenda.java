package com.example.direktoratpendidikan;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.data.Agenda;

public class DetailAgenda extends AppCompatActivity {

    public String agenda_id;
    private TextView tanggal_kegiatan;
    private TextView nama_kegiatan;
    private TextView bulantahun;
    public ImageView onback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);
        this.agenda_id = getIntent().getStringExtra("idAgenda");
        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tanggal_kegiatan = findViewById(R.id.tanggaldetail);
        nama_kegiatan = findViewById(R.id.nama_kegiatan_detail);
        bulantahun = findViewById(R.id.bulantahun);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String tgl =(String) b.get("tanggalKegiatan");
            tanggal_kegiatan.setText(tgl);
            String blnthn =(String) b.get("bulanTahun");
            bulantahun.setText(blnthn);
            String nama =(String) b.get("namaKegiatan");
            nama_kegiatan.setText(nama);
        }

    }


    public String getId_agenda(){
        return this.agenda_id;
    }
}
