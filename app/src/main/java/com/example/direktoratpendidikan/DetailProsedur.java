package com.example.direktoratpendidikan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.api.ApiClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailProsedur extends AppCompatActivity {

    public String prosedur_id;
    private TextView judulprosedur, tglpublish, idprosedur, kontenprosedur, klikgambar;
    public ImageView onback, gambarprosedur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prosedur);
        this.prosedur_id = getIntent().getStringExtra("prosedurid");
        onback = (ImageView) findViewById(R.id.prosedurkembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        judulprosedur = findViewById(R.id.judulprosedur_detail);
        tglpublish = findViewById(R.id.tgl_publikasi);
        //idbeasiswa = findViewById(R.id.idbeasiswa);
        kontenprosedur = findViewById(R.id.kontenprosedurdetail);
        klikgambar = findViewById(R.id.klikgambar);
        gambarprosedur = findViewById(R.id.gambarprosedur);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String judul =(String) b.get("judulprosedur");
            judulprosedur.setText(judul);
            judulprosedur.setTextIsSelectable(true);
//            String id =(String) b.get("idbeasiswa");
//            idbeasiswa.setText(id);

            String konten =(String) b.get("kontenpanjangprosedur");
            kontenprosedur.setText(konten);
            kontenprosedur.setTextIsSelectable(true);

            String tgl =(String) b.get("tglpublish");
            tglpublish.setText(tgl);

            final String gambar = (String) b.get("gambarprosedur");
            Picasso.with(getApplicationContext()).load(ApiClient.PROSEDUR_IMAGE_URL+gambar).into(gambarprosedur, new Callback() {
            @Override
            public void onSuccess() {
                klikgambar.setText("(klik gambar untuk memperbesar)");
                gambarprosedur.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = ApiClient.PROSEDUR_IMAGE_URL+gambar;
                        Intent i = new Intent(v.getContext(), PerbesarGambar.class);
                        i.putExtra("gambar", url);
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onError() {
                gambarprosedur.setVisibility(View.GONE);
                klikgambar.setVisibility(View.GONE);
            }
        });
        }

        }
}
