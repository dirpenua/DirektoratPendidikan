package com.example.direktoratpendidikan.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.TambahPeserta;
import com.example.direktoratpendidikan.adapter.AdapterPesertaAda;
import com.example.direktoratpendidikan.adapter.AdapterTambahPeserta;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Dosen;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPesertaDitambahkan extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List pesertaList;
    private AdapterPesertaAda adapterpa;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    public TabPesertaDitambahkan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_peserta_ditambahkan, container, false);

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerView);

        final String agendaid = getActivity().getIntent().getStringExtra("idagenda");

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchPesertaAda("ada", agendaid);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPesertaAda("ada", agendaid);
            }
        });
        return view;
    }

    public void fetchPesertaAda (String type, String idagenda){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Dosen>> call = apiInterface.getPesertaAda(type, idagenda);
        call.enqueue(new Callback<List<Dosen>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dosen>> call, @NonNull Response<List<Dosen>> response) {
                progressBar.setVisibility(View.GONE);
                pesertaList = response.body();
                swipeContainer.setRefreshing(false);
                adapterpa = new AdapterPesertaAda(getContext(), pesertaList);
                recyclerView.setAdapter(adapterpa);
                adapterpa.notifyDataSetChanged();
                Log.e("tesPesertaBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Dosen>> call, @NonNull Throwable t) {
                Log.e("tesPesertaGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
