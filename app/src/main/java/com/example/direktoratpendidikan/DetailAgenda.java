package com.example.direktoratpendidikan;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.direktoratpendidikan.data.Agenda;

public class DetailAgenda extends AppCompatActivity {

    public String agenda_id;
    private TextView tanggal_kegiatan, jam_mulai, jam_selesai, nama_kegiatan, bulantahun, tempat, jumlahundangan, narahubung;
    public ImageView onback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);

        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tanggal_kegiatan = findViewById(R.id.tanggaldetail);
        jam_mulai = findViewById(R.id.jammulai);
        jam_selesai = findViewById(R.id.jamselesai);
        nama_kegiatan = findViewById(R.id.nama_kegiatan_detail);
        bulantahun = findViewById(R.id.bulantahun);
        tempat = findViewById(R.id.tempatdetail);
        jumlahundangan = findViewById(R.id.jumlahundangan);
        narahubung = findViewById(R.id.narahubungdetail);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String tgl =(String) b.get("tanggalKegiatan");
            tanggal_kegiatan.setText(tgl);
            String jm =(String) b.get("jamMulai");
            jam_mulai.setText(jm);
            String js =(String) b.get("jamSelesai");
            jam_selesai.setText(js);
            String blnthn =(String) b.get("bulanTahun");
            bulantahun.setText(blnthn);
            String nama =(String) b.get("namaKegiatan");
            nama_kegiatan.setText(nama);
            String tmpt =(String) b.get("tempatKegiatan");
            tempat.setText(tmpt);
            String jmlund =(String) b.get("jumlahUndangan");
            jumlahundangan.setText(jmlund);
            final String nrhb =(String) b.get("nohpNarahubung");
            narahubung.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = "https://wa.me/" + nrhb;
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "Nomor ini tidak terhubung dengan akun WhatsApp manapun",  Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
            narahubung.setText("+"+nrhb);
            narahubung.setTextIsSelectable(true);

        }

    }

    public String getId_agenda(){
        return this.agenda_id;
    }
}
