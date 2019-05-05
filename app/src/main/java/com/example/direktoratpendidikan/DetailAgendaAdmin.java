package com.example.direktoratpendidikan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailAgendaAdmin extends AppCompatActivity {

    public String agenda_id;
    private TextView tanggal_kegiatan, jam_mulai, jam_selesai, nama_kegiatan, bulantahun, tempat, jumlahundangan, narahubung;
    public ImageView onback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda_admin);

        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tanggal_kegiatan = findViewById(R.id.tanggaldetail);
        nama_kegiatan = findViewById(R.id.nama_kegiatan_detail);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
//            String tgl =(String) b.get("tanggalKegiatan");
//            tanggal_kegiatan.setText(tgl);
            String nama =(String) b.get("idagenda");
            nama_kegiatan.setText(nama);

        }


    }
}
