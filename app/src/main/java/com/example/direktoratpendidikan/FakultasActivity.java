package com.example.direktoratpendidikan;

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

import com.example.direktoratpendidikan.adapter.AdapterBerita;
import com.example.direktoratpendidikan.adapter.AdapterFakultas;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Fakultas;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FakultasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SwipeRefreshLayout swipeContainer;
    private SearchView cariFakultas;
    public ImageView onback;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List fakultasList;
    private AdapterFakultas adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<Fakultas> fetchCari = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakultas);
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerViewFakultas);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cariFakultas = findViewById(R.id.cari_fakultas);
        cariFakultas.setQueryHint("Cari fakultas...");
        cariFakultas.setIconified(false);
        cariFakultas.setOnQueryTextListener(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        fetchFakultas(1);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFakultas(1);
            }
        });
    }

    public void fetchFakultas (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Fakultas>> call = apiInterface.getFakultas(kategori);
        call.enqueue(new Callback<List<Fakultas>>() {
            @Override
            public void onResponse(@NonNull Call<List<Fakultas>> call, @NonNull Response<List<Fakultas>> response) {
                progressBar.setVisibility(View.GONE);
                fakultasList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterFakultas(getApplicationContext(),fakultasList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesFakultasBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Fakultas>> call, @NonNull Throwable t) {
                Log.e("tesFakultasGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Cari> call = apiInterface.searchFakultas(newText);
        Log.e("NEWTEXT ", newText);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                if (value.equals("1")) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchCari = response.body().getListFakultas();
                    adapter = new AdapterFakultas(getApplicationContext(), fetchCari);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    //View v = LayoutInflater.from(getLayoutInflater().getContext()).inflate(R.layout.noresults, parent, false);
                }
            }

            @Override
            public void onFailure(Call<Cari> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }

}
