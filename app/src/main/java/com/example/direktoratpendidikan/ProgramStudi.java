package com.example.direktoratpendidikan;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.adapter.AdapterDownload;
import com.example.direktoratpendidikan.adapter.AdapterProdi;
import com.example.direktoratpendidikan.adapter.AdapterProsedur;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.Fakultas;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramStudi extends AppCompatActivity {

    private TextView namafakultas, notelp, kampusfakultas, alamatfakultas, faxfakultas;
    public ImageView onback, gambarfakultas, lokasi, telpon, email;
    private ApiInterface apiInterface;
    private AdapterProdi adapter;
    private List prodiList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_studi);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerViewProdi);
        namafakultas = findViewById(R.id.namafakultasdetail);
        gambarfakultas = findViewById(R.id.gambarfakultasdetail);
        notelp = findViewById(R.id.telpfakultas);
        kampusfakultas = findViewById(R.id.kampusfakultas);
        alamatfakultas = findViewById(R.id.alamat);
        faxfakultas = findViewById(R.id.faxfakultas);
        lokasi = findViewById(R.id.ic_lokasi);
        telpon = findViewById(R.id.ic_telp);
        email = findViewById(R.id.ic_email);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            final String idfakultas =(String) b.get("idfakultas");
            fetchProdi(idfakultas);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchProdi(idfakultas);
                }
            });

            String nama =(String) b.get("namafakultas");
            namafakultas.setText(nama);

            final String gambar = (String) b.get("gambarfakultas");
            Picasso.with(getApplicationContext()).load(ApiClient.FAKUL_IMAGE_URL+gambar).error(R.drawable.ic_progress).into(gambarfakultas);

            final String telp =(String) b.get("notelpfakultas");
            notelp.setText("Telp. "+telp);

            String kampus =(String) b.get("kampusfakultas");
            kampusfakultas.setText(kampus);

            String alamat =(String) b.get("alamatfakultas");
            alamatfakultas.setText(alamat);

            String fax =(String) b.get("faxfakultas");
            faxfakultas.setText("Fax. "+fax);

            final String gmaps =(String) b.get("apigmapsfakultas");
            lokasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = gmaps;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    try {
                        startActivity(i);
                    } catch (android.content.ActivityNotFoundException ex) {
                        String text = "Tidak dapat menemukan lokasi. Harap hubungi admin melalui menu BANTUAN";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(getApplicationContext(),centeredText , Toast.LENGTH_SHORT).show();
                    }
                }
            });

            telpon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+telp));

                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        String text = "Tidak dapat menemukan nomor telepon. Harap hubungi admin melalui menu BANTUAN";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(getApplicationContext(),centeredText , Toast.LENGTH_SHORT).show();
                    }
                }
            });

           final String mail = (String) b.get("emailfakultas");
           email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { mail});

                    try {
                        startActivity(Intent.createChooser(intent, "Email via..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        String text = "Tidak dapat menemukan email. Harap hubungi admin melalui menu BANTUAN";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(getApplicationContext(),centeredText , Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void fetchProdi (String id){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Fakultas>> call = apiInterface.getProdi(id);
        call.enqueue(new Callback<List<Fakultas>>() {
            @Override
            public void onResponse(@NonNull Call<List<Fakultas>> call, @NonNull Response<List<Fakultas>> response) {
                prodiList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterProdi(getApplicationContext(), prodiList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesProsedurBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Fakultas>> call, @NonNull Throwable t) {
                Log.e("tesProsedurGagal", "Gagal");
            }
        });
    }

}
