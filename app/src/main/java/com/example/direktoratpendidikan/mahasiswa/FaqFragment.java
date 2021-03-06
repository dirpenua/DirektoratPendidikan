package com.example.direktoratpendidikan.mahasiswa;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.adapter.AdapterBeasiswa;
import com.example.direktoratpendidikan.adapter.AdapterBerita;
import com.example.direktoratpendidikan.adapter.AdapterFaq;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.FAQ;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SwipeRefreshLayout swipeContainer;
    private SearchView cariFAQ;
    FragmentActivity mActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List faqList;
    private AdapterFaq adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<FAQ> fetchCari = new ArrayList<>();

    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerView);

        cariFAQ = view.findViewById(R.id.carifaq );
        cariFAQ.setQueryHint("Cari pertanyaan...");
        cariFAQ.setIconified(false);
        cariFAQ.setOnQueryTextListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        progressBar.setVisibility(View.VISIBLE);
        fetchFAQ("faq");
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFAQ("faq");
            }
        });

        return view;
    }

    public void fetchFAQ (String kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<FAQ>> call = apiInterface.getFAQ(kategori);
        call.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(@NonNull Call<List<FAQ>> call, @NonNull Response<List<FAQ>> response) {
                progressBar.setVisibility(View.GONE);
                faqList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterFaq(getActivity(), faqList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.e("tesFAQBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<FAQ>> call, @NonNull Throwable t) {
                Log.e("tesFAQGagal", "Gagal");
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
        Call<Cari> call = apiInterface.searchFaq(newText);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                if (value.equals("1")) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchCari = response.body().getListFAQ();
                    adapter = new AdapterFaq(getActivity(), fetchCari);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Tidak ada hasil untuk pencarian ini",Toast.LENGTH_SHORT).show();
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
