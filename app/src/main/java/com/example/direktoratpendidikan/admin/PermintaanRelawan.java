package com.example.direktoratpendidikan.admin;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.adapter.AdapterPermintaanRelawan;
import com.example.direktoratpendidikan.adapter.AdapterRelawanMBK;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.RelawanMBK;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermintaanRelawan extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List relawanmbkList;
    private AdapterPermintaanRelawan adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    ImageView onback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_relawan);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchRelawan("permintaan");
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRelawan("permintaan");
            }
        });
    }

    public void fetchRelawan (String type){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RelawanMBK>> call = apiInterface.getPermintaanRelawan(type);
        Log.e("tipe", type);
        call.enqueue(new Callback<List<RelawanMBK>>() {
            @Override
            public void onResponse(@NonNull Call<List<RelawanMBK>> call, @NonNull Response<List<RelawanMBK>> response) {
                progressBar.setVisibility(View.GONE);
                relawanmbkList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterPermintaanRelawan(getApplicationContext(), relawanmbkList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesPermintaanBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<RelawanMBK>> call, @NonNull Throwable t) {
                Log.e("tesPermintaanGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
