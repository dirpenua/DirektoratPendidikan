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

import com.example.direktoratpendidikan.adapter.AdapterBeasiswa;
import com.example.direktoratpendidikan.adapter.AdapterBerita;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Cari;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SwipeRefreshLayout swipeContainer;
    private SearchView cariBerita;
    public ImageView onback;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List beritaList;
    private AdapterBerita adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<Berita> fetchCari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerViewBerita);

        onback = (ImageView) findViewById(R.id.kembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cariBerita = findViewById(R.id.cari_berita);
        cariBerita.setQueryHint("Cari berita...");
        cariBerita.setIconified(false);
        cariBerita.setOnQueryTextListener(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        fetchBerita(1);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchBerita(1);
            }
        });
    }

    public void fetchBerita (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Berita>> call = apiInterface.getBerita(kategori);
        call.enqueue(new Callback<List<Berita>>() {
            @Override
            public void onResponse(@NonNull Call<List<Berita>> call, @NonNull Response<List<Berita>> response) {
                progressBar.setVisibility(View.GONE);
                beritaList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterBerita(getApplicationContext(), beritaList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                    adapter.SetOnItemClickListener(new Adapter.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(View v , int position) {
//                            Intent i = new Intent(getActivity(), DetailAgenda.class);
//                            i.putExtra("namaKegiatan",);
//                            startActivity(i);
//                        }
//                    });
                Log.e("tesBeritaBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Berita>> call, @NonNull Throwable t) {
                Log.e("tesBeritaGagal", "Gagal");
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
        Call<Cari> call = apiInterface.searchBerita(newText);
        Log.e("NEWTEXT ", newText);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                if (value.equals("1")) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchCari = response.body().getListBerita();
                    adapter = new AdapterBerita(getApplicationContext(), fetchCari);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    //View v = LayoutInflater.from(getLayoutInflater().getContext()).inflate(R.layout.noresults, parent, false);
                }

                }
//                if (value.equals("1")) {
//                    fetchCari = response.body().getListBeasiswa();
//                    adapter = new AdapterBeasiswa(getActivity(), fetchCari);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    //View v = LayoutInflater.from(getLayoutInflater().getContext()).inflate(R.layout.noresults, parent, false);
//                }

            @Override
            public void onFailure(Call<Cari> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }

}
