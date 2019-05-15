package com.example.direktoratpendidikan.admin;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.SpinnerCariAdapter;
import com.example.direktoratpendidikan.adapter.AdapterDownload;
import com.example.direktoratpendidikan.adapter.AdapterDownloadPros;
import com.example.direktoratpendidikan.adapter.AdapterProsedur;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.Prosedur;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProsedurAdminFragment extends Fragment implements SearchView.OnQueryTextListener{


    public ProsedurAdminFragment() {
        // Required empty public constructor
    }

    private SwipeRefreshLayout swipeContainer;
    private SearchView cariProsedur;
    private Spinner pSpinner;
    FragmentActivity mActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List prosedurList;
    private AdapterProsedur adapterpro;
    private AdapterDownload adapterdw;
    private AdapterDownloadPros adapterdwpros;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<Prosedur> fetchCari = new ArrayList<>();
    private List<Download> fetchCariDw = new ArrayList<>();

    SharedPreferences sharedpreferences;
    public final static String TAG_NIPNIK = "nipnik";
    String nipnik;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prosedur_admin, container, false);

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerViewProsedur);

        cariProsedur = view.findViewById(R.id.cari_prosedur);
        cariProsedur.setQueryHint("Cari prosedur...");
        cariProsedur.setIconified(false);
        cariProsedur.setOnQueryTextListener(this);

        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);
        pSpinner = view.findViewById(R.id.spinner_prosedur);


        String[] items = getResources().getStringArray(R.array.kategoriprosedur);
        List<String> spinnerItems = new ArrayList<String>();

        for (int i = 0; i < items.length; i++) {
            spinnerItems.add(items[i]);
        }

        SpinnerCariAdapter spinneradapter = new SpinnerCariAdapter(((AppCompatActivity) getActivity()), spinnerItems);
        pSpinner.setAdapter(spinneradapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);


        pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                switch(position){
                    case 0:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchProsedur(0);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchProsedur(0);
                            }
                        });
                        break;
                    case 1:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchDownload(1); //pengembangan pendidikan
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchDownload(1);
                            }
                        });
                        break;
                    case 2:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchDownloadPros(2);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchDownloadPros(2);
                            }
                        });
                        break;
                    case 3:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchDownloadPros(3);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchDownloadPros(3);
                            }
                        });
                        break;
                    case 4:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchDownloadPros(4);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchDownloadPros(4);
                            }
                        });
                        break;
                    default:

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            pSpinner.setDropDownVerticalOffset(-116);
        }

        return view;
    }

    public void fetchProsedur (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Prosedur>> call = apiInterface.getProsedur(kategori);
        call.enqueue(new Callback<List<Prosedur>>() {
            @Override
            public void onResponse(@NonNull Call<List<Prosedur>> call, @NonNull Response<List<Prosedur>> response) {
                progressBar.setVisibility(View.GONE);
                prosedurList = response.body();
                swipeContainer.setRefreshing(false);
                adapterpro = new AdapterProsedur(getActivity(), prosedurList);
                recyclerView.setAdapter(adapterpro);
                adapterpro.notifyDataSetChanged();
                Log.e("tesProsedurBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Prosedur>> call, @NonNull Throwable t) {
                Log.e("tesProsedurGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void fetchDownload (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Download>> call = apiInterface.getDw(kategori);
        call.enqueue(new Callback<List<Download>>() {
            @Override
            public void onResponse(@NonNull Call<List<Download>> call, @NonNull Response<List<Download>> response) {
                progressBar.setVisibility(View.GONE);
                prosedurList = response.body();
                swipeContainer.setRefreshing(false);
                adapterdw = new AdapterDownload(getActivity(), prosedurList);
                recyclerView.setAdapter(adapterdw);
                adapterdw.notifyDataSetChanged();
                Log.e("tesProsedurBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Download>> call, @NonNull Throwable t) {
                Log.e("tesProsedurGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void fetchDownloadPros (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Download>> call = apiInterface.getDw(kategori);
        call.enqueue(new Callback<List<Download>>() {
            @Override
            public void onResponse(@NonNull Call<List<Download>> call, @NonNull Response<List<Download>> response) {
                progressBar.setVisibility(View.GONE);
                prosedurList = response.body();
                swipeContainer.setRefreshing(false);
                adapterdwpros = new AdapterDownloadPros(getActivity(), prosedurList);
                recyclerView.setAdapter(adapterdwpros);
                adapterdwpros.notifyDataSetChanged();
                Log.e("tesProsedurBerhasil", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Download>> call, @NonNull Throwable t) {
                Log.e("tesProsedurGagal", "Gagal");
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
        String kategori = pSpinner.getSelectedItem().toString();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Cari> call = apiInterface.searchProsedur(newText, kategori);
        Log.e("NEWTEXT ", newText);
        Log.e("Posisinya dimana", "Value " + kategori);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                switch(value){
                    case "0":
                        fetchCari = response.body().getListProsedur();
                        adapterpro = new AdapterProsedur(getActivity(), fetchCari);
                        recyclerView.setAdapter(adapterpro);
                        adapterpro.notifyDataSetChanged();
                        break;

                    case "1":
                        fetchCariDw = response.body().getListDownload();
                        adapterdw = new AdapterDownload(getActivity(), fetchCariDw);
                        recyclerView.setAdapter(adapterdw);
                        adapterdw.notifyDataSetChanged();
                        break;

                    case "2":
                        fetchCariDw = response.body().getListDownload();
                        adapterdwpros = new AdapterDownloadPros(getActivity(), fetchCariDw);
                        recyclerView.setAdapter(adapterdwpros);
                        adapterdwpros.notifyDataSetChanged();
                        break;

                    case "3":
                        fetchCariDw = response.body().getListDownload();
                        adapterdwpros = new AdapterDownloadPros(getActivity(), fetchCariDw);
                        recyclerView.setAdapter(adapterdwpros);
                        adapterdwpros.notifyDataSetChanged();
                        break;

                    case "4":
                        fetchCariDw = response.body().getListDownload();
                        adapterdwpros = new AdapterDownloadPros(getActivity(), fetchCariDw);
                        recyclerView.setAdapter(adapterdwpros);
                        adapterdwpros.notifyDataSetChanged();
                        break;
                }
//                if (value.equals("1")) {
//                    fetchCari = response.body().getListBeasiswa();
//                    adapter = new AdapterBeasiswa(getActivity(), fetchCari);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    //View v = LayoutInflater.from(getLayoutInflater().getContext()).inflate(R.layout.noresults, parent, false);
//                }
            }

            @Override
            public void onFailure(Call<Cari> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }

}
