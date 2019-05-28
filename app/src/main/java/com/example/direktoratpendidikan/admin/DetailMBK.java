package com.example.direktoratpendidikan.admin;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.RelawanMBK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMBK extends AppCompatActivity {

    public String relawan_id;
    private TextView nim, namalengkap, jenisdisabilitas, nohp, fakultas, prodi, email, idrelawan;
    public ImageView onback, nonaktif;
    private SwipeRefreshLayout swipeContainer;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mbk);
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
        jenisdisabilitas = findViewById(R.id.detailjenisdisabilitas);
        nohp = findViewById(R.id.detailhp);
        fakultas = findViewById(R.id.detailfakultas);
        prodi = findViewById(R.id.detailprodi);
        email = findViewById(R.id.detailemail);

        idrelawan = findViewById(R.id.idrelawan);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            final String nimmbk =(String) b.get("nim");
            //idrelawan.setText(nimmbk);
            fetchDetailMBK(nimmbk);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchDetailMBK(nimmbk);
                }
            });

        }
    }

    public void fetchDetailMBK (String nimmbk){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RelawanMBK> userCall = apiInterface.getDetailMBK(nimmbk);
        userCall.enqueue(new Callback<RelawanMBK>() {
            @Override
            public void onResponse(Call<RelawanMBK> call, Response<RelawanMBK> response) {
                //Log.d("onResponse", "" + response.body().getMessage());
                progressBar.setVisibility(View.GONE);
                nim.setText(response.body().getNim());
                namalengkap.setText(response.body().getNama());
                jenisdisabilitas.setText(response.body().getDisabilitas());
                fakultas.setText(response.body().getFakultas());
                prodi.setText(response.body().getProdi());
                email.setText(response.body().getEmail());
                final String nomerhp =response.body().getNohp();
                if(nomerhp.length() == 0 || nomerhp == null){
                    nohp.setText(" Nomor HP tidak ada");
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
