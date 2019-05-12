package com.example.direktoratpendidikan.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.TambahPeserta;
import com.example.direktoratpendidikan.adapter.AdapterPesertaAda;
import com.example.direktoratpendidikan.adapter.AdapterTambahPeserta;
import com.example.direktoratpendidikan.adapter.SpinnerCariHitamAdapter;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Dosen;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPesertaDitambahkan extends Fragment implements SearchView.OnQueryTextListener{

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private SearchView cari;
    private Spinner pSpinner;
    private RecyclerView.LayoutManager layoutManager;
    private List pesertaList;
    private AdapterPesertaAda adapterpa;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<Dosen> fetchCari = new ArrayList<>();

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
        cari = view.findViewById(R.id.cari);
        cari.setQueryHint("Cari peserta...");
        cari.setIconified(false);
        cari.setOnQueryTextListener(this);

        pSpinner = view.findViewById(R.id.spinner_peserta);

        final String agendaid = getActivity().getIntent().getStringExtra("idagenda");

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String[] items = getResources().getStringArray(R.array.kategoripeserta);
        List<String> spinnerItems = new ArrayList<String>();

        for (int i = 0; i < items.length; i++) {
            spinnerItems.add(items[i]);
        }

        SpinnerCariHitamAdapter spinneradapter = new SpinnerCariHitamAdapter(((AppCompatActivity) getActivity()), spinnerItems);
        pSpinner.setAdapter(spinneradapter);

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String agendaid = getActivity().getIntent().getStringExtra("idagenda");
        String kategori = pSpinner.getSelectedItem().toString();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Cari> call = apiInterface.searchPesertaAda(newText, agendaid, kategori);
        Log.e("NEWTEXT ", newText);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                if (value.equals("1")) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchCari = response.body().getListPesertaAda();
                    adapterpa = new AdapterPesertaAda(getContext(), fetchCari);
                    recyclerView.setAdapter(adapterpa);
                    adapterpa.notifyDataSetChanged();
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
