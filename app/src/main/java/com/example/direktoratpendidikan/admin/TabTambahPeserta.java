package com.example.direktoratpendidikan.admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.TambahPeserta;
import com.example.direktoratpendidikan.adapter.AdapterDownload;
import com.example.direktoratpendidikan.adapter.AdapterTambahPeserta;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Dosen;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.MSG;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabTambahPeserta extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    TextView setagendaid;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List pesertaList;
    private AdapterTambahPeserta adaptertp;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_tambah_peserta, container, false);

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

        fetchPeserta("semua", agendaid);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPeserta("semua", agendaid);
            }
        });
        return view;
    }


    public void fetchPeserta (String type, String idagenda){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Dosen>> call = apiInterface.getListPeserta(type, idagenda);
        Log.e("tipe", type);
        call.enqueue(new Callback<List<Dosen>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dosen>> call, @NonNull Response<List<Dosen>> response) {
                progressBar.setVisibility(View.GONE);
                pesertaList = response.body();
                swipeContainer.setRefreshing(false);
                adaptertp = new AdapterTambahPeserta(getContext(), pesertaList);
                recyclerView.setAdapter(adaptertp);
                adaptertp.notifyDataSetChanged();
                Log.e("tesPesertaBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Dosen>> call, @NonNull Throwable t) {
                Log.e("tesPesertaGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void tambahPeserta(String agendaid, String nipnik){
        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MSG> userCall = service.tambahPeserta(agendaid, nipnik);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                Log.d("SUKSERNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                if(response.body().getSuccess() == 1) {
                    String text = response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getContext(),centeredText, Toast.LENGTH_LONG).show();
                }else {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getContext(),centeredText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
//                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }




}
