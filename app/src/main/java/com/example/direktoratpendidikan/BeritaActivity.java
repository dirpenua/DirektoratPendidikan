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
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private SearchView cariBerita;
    public ImageView onback;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List beritaList;
    private AdapterBerita adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

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
                Log.e("tesBeasiswaBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Berita>> call, @NonNull Throwable t) {
                Log.e("tesBeasiswaGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
