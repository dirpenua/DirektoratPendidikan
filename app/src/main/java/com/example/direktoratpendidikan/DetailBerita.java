package com.example.direktoratpendidikan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.api.ApiClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailBerita extends AppCompatActivity {

    private TextView judulberita, tglpublish, kontenberita, klikgambar;
    public ImageView onback, gambarberita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        onback = (ImageView) findViewById(R.id.beritakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        judulberita = findViewById(R.id.judulberita_detail);
        tglpublish = findViewById(R.id.tgl_publikasi);
        kontenberita = findViewById(R.id.kontenberitadetail);
        klikgambar = findViewById(R.id.klikgambar);
        gambarberita = findViewById(R.id.gambarberita);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String judul =(String) b.get("judulberita");
            judulberita.setText(judul);
            judulberita.setTextIsSelectable(true);
//            String id =(String) b.get("idbeasiswa");
//            idbeasiswa.setText(id);

            String konten =(String) b.get("kontenberita");
            kontenberita.setText(konten);
            kontenberita.setTextIsSelectable(true);

            String tgl =(String) b.get("tglpublish");
            tglpublish.setText(tgl);

            final String gambar = (String) b.get("gambarberita");
            Picasso.with(getApplicationContext()).load(ApiClient.BERITA_IMAGE_URL+gambar).into(gambarberita, new Callback() {
                @Override
                public void onSuccess() {
                    klikgambar.setText("(klik gambar untuk memperbesar)");
                    gambarberita.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = ApiClient.BERITA_IMAGE_URL+gambar;
//                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            Intent i = new Intent(v.getContext(), PerbesarGambar.class);
                            i.putExtra("gambar", url);
                            v.getContext().startActivity(i);
                        }
                    });
                }

                @Override
                public void onError() {
                    gambarberita.setVisibility(View.GONE);
                    klikgambar.setVisibility(View.GONE);
                }
            });
        }

    }
}

