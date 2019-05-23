package com.example.direktoratpendidikan.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.TambahPeserta;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.RelawanMBK;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRelawanMBK extends AppCompatActivity {

    public String relawan_id;
    private TextView nim, namalengkap, namapanggilan, alamatrumah, alamatkosan, asalkota, asalprovinsi, nohp, fakultas, prodi, semester, ipkterakhir, prestasi, idrelawan;
    public ImageView onback, nonaktif;
    private SwipeRefreshLayout swipeContainer;
    private ApiInterface apiInterface;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_relawan_mbk);
        progressBar = findViewById(R.id.prograss);
        onback = (ImageView) findViewById(R.id.kembali);
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
        nim = findViewById(R.id.detailnim);
        namalengkap = findViewById(R.id.detailnamalengkap);
        namapanggilan = findViewById(R.id.detailnamapanggil);
        alamatrumah = findViewById(R.id.detailrumah);
        alamatkosan = findViewById(R.id.detailkos);
        asalkota = findViewById(R.id.detailkota);
        asalprovinsi = findViewById(R.id.detailprovinsi);
        nohp = findViewById(R.id.detailhp);
        fakultas = findViewById(R.id.detailfakultas);
        prodi = findViewById(R.id.detailprodi);
        semester = findViewById(R.id.detailsemester);
        ipkterakhir = findViewById(R.id.detailipk);
        prestasi = findViewById(R.id.detailprestasi);

        idrelawan = findViewById(R.id.idrelawan);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            final String nimrelawan =(String) b.get("nim");
            fetchDetailRelawan(nimrelawan);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchDetailRelawan(nimrelawan);
                }
            });

        }
    }

    public void fetchDetailRelawan (String nimrelawan){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RelawanMBK> userCall = apiInterface.getDetailRelawan(nimrelawan);
        final String nimrel = getIntent().getStringExtra("nim");
        userCall.enqueue(new Callback<RelawanMBK>() {
            @Override
            public void onResponse(Call<RelawanMBK> call, Response<RelawanMBK> response) {
                //Log.d("onResponse", "" + response.body().getMessage());
                progressBar.setVisibility(View.GONE);
                nim.setText(response.body().getNim());
                namalengkap.setText(response.body().getNama());
                namapanggilan.setText(response.body().getNamapanggilan());
                alamatrumah.setText(response.body().getAlamatrumah());
                alamatkosan.setText(response.body().getAlamatkos());
                asalkota.setText(response.body().getAsalkota());
                asalprovinsi.setText(response.body().getAsalprovinsi());
                fakultas.setText(response.body().getFakultas());
                prodi.setText(response.body().getProdi());
                semester.setText(response.body().getSemester());
                ipkterakhir.setText(response.body().getIpk());
                prestasi.setText(response.body().getPrestasi());
                final String nomerhp =response.body().getNohp();
                if(nomerhp.length() == 0 || nomerhp == null){
                    nohp.setText("Nomor HP tidak ada");
                }
                else{
                    nohp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String url = "https://wa.me/62" + nomerhp;
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(v.getContext(), "Nomor ini tidak terhubung dengan akun WhatsApp manapun",  Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
                    nohp.setText(" 0"+nomerhp);
                    nohp.setTextIsSelectable(true);
                }

            }

            @Override
            public void onFailure(Call<RelawanMBK> call, Throwable t) {
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
