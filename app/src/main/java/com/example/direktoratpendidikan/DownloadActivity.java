package com.example.direktoratpendidikan;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.adapter.AdapterDownload;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Download;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadActivity extends AppCompatActivity {

    public ImageView onback;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List downloadList;
    private AdapterDownload adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);

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

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchDownload("download");
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDownload("download");
            }
        });
    }

    public void fetchDownload (String type){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Download>> call = apiInterface.getDownload(type);
        Log.e("tipe", type);
        call.enqueue(new Callback<List<Download>>() {
            @Override
            public void onResponse(@NonNull Call<List<Download>> call, @NonNull Response<List<Download>> response) {
                progressBar.setVisibility(View.GONE);
                downloadList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterDownload(getApplicationContext(), downloadList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesDownloadBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Download>> call, @NonNull Throwable t) {
                Log.e("tesDownloadGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
