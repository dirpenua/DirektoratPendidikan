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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAgenda extends AppCompatActivity {

    public String agenda_id;
    private TextView tanggal_kegiatan, jam_mulai, jam_selesai, nama_kegiatan, bulantahun, tempat, jumlahundangan, narahubung;
    public ImageView onback, tambahpeserta;
    private SwipeRefreshLayout swipeContainer;
    private ApiInterface apiInterface;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);

        progressBar = findViewById(R.id.prograss);
        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);


        tanggal_kegiatan = findViewById(R.id.tanggaldetail);
        nama_kegiatan = findViewById(R.id.nama_kegiatan_detail);
        jam_mulai = findViewById(R.id.jammulai);
        jam_selesai = findViewById(R.id.jamselesai);
        bulantahun = findViewById(R.id.bulantahun);
        tempat = findViewById(R.id.tempatdetail);
        jumlahundangan = findViewById(R.id.jumlahundangan);
        narahubung = findViewById(R.id.narahubungdetail);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            final String idagenda =(String) b.get("idagenda");
            fetchDetailAgenda(idagenda);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchDetailAgenda(idagenda);
                }
            });

        }
    }

    public void fetchDetailAgenda (String idagenda){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Agenda> userCall = apiInterface.getDetailAgenda(idagenda);
        userCall.enqueue(new Callback<Agenda>() {
            @Override
            public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                //Log.d("onResponse", "" + response.body().getMessage());
                progressBar.setVisibility(View.GONE);
                tanggal_kegiatan.setText(response.body().getTanggal());
                jam_mulai.setText(response.body().getJammulai());
                jam_selesai.setText(response.body().getJamselesai());
                bulantahun.setText(response.body().getBulantahun());
                nama_kegiatan.setText(response.body().getNama());
                tempat.setText(response.body().getTempat());
                jumlahundangan.setText(response.body().getJumlahUndangan());
                final String nrhb =response.body().getNarahubung();
                if(nrhb!=null){
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
                else{
                    narahubung.setText("Narahubung tidak tersedia");
                }

            }

            @Override
            public void onFailure(Call<Agenda> call, Throwable t) {
                Log.d("onFailure", t.toString());
                String text = "Koneksi sedang tidak stabil. Refresh halaman atau tunggu beberepa saat";
                SpannableString centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }
}
