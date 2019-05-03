package com.example.direktoratpendidikan;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.adapter.AdapterAkreditasi;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Akreditasi;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AkreditasiActivity extends AppCompatActivity {

    public ImageView onback, gambarakreditasi;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List akreditasiList;
    private AdapterAkreditasi adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akreditasi);
        recyclerView = findViewById(R.id.recyclerViewAkreditasi);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressBar = findViewById(R.id.prograss);

//        swipeContainer = view.findViewById(R.id.swipeContainer);
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_blue_bright);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fetchAkreditasi(1);

        gambarakreditasi = findViewById(R.id.gambarakreditasi);

        final String gambar = "akreditasi.jpg";
        Picasso.with(getApplicationContext()).load(ApiClient.IMAGE_URL + gambar).into(gambarakreditasi, new Callback() {
            @Override
            public void onSuccess() {
                gambarakreditasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = ApiClient.IMAGE_URL + gambar;
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "Grafik tidak ditemukan. Harap hubungi admin melalui menu BANTUAN", Toast.LENGTH_SHORT);
            }
        });
    }

    public void fetchAkreditasi (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Akreditasi>> call = apiInterface.getAkreditasi(kategori);
        call.enqueue(new retrofit2.Callback<List<Akreditasi>>() {
            @Override
            public void onResponse(@NonNull Call<List<Akreditasi>> call, @NonNull Response<List<Akreditasi>> response) {
                progressBar.setVisibility(View.GONE);
                akreditasiList = response.body();
                //swipeContainer.setRefreshing(false);
                adapter = new AdapterAkreditasi(getApplicationContext(), akreditasiList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesAkreBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Akreditasi>> call, @NonNull Throwable t) {
                Log.e("tesAkreGagal", "Gagal");
            }
        });
    }
}
