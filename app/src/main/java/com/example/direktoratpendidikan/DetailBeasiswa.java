package com.example.direktoratpendidikan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailBeasiswa extends AppCompatActivity {

    public String beasiswa_id;
    private TextView judulbeasiswa, tglpublish, idbeasiswa, kontenbeasiswa;
    public ImageView onback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beasiswa);
        this.beasiswa_id = getIntent().getStringExtra("beasiswaid");
        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        judulbeasiswa = findViewById(R.id.judulbeasiswa_detail);
        tglpublish = findViewById(R.id.tgl_publikasi);
        idbeasiswa = findViewById(R.id.idbeasiswa);
        kontenbeasiswa = findViewById(R.id.kontenbeasiswadetail);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String judul =(String) b.get("judulbeasiswa");
            judulbeasiswa.setText(judul);
            String id =(String) b.get("idbeasiswa");
            idbeasiswa.setText(id);
            String konten =(String) b.get("kontenpanjangbeasiswa");
            kontenbeasiswa.setText(konten);
            String tgl =(String) b.get("tglpublish");
            tglpublish.setText(tgl);

        }

    }
}
