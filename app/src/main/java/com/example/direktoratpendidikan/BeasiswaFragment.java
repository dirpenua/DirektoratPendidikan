package com.example.direktoratpendidikan;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.direktoratpendidikan.adapter.AdapterBeasiswa;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Cari;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeasiswaFragment extends Fragment implements SearchView.OnQueryTextListener {



    public BeasiswaFragment() {
        // Required empty public constructor
    }

    private SearchView cariBeasiswa;
    private Spinner bSpinner;
    FragmentActivity mActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List beasiswaList;
    private AdapterBeasiswa                                                                adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    private List<Beasiswa> fetchCari = new ArrayList<>();

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beasiswa, container, false);

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerViewBeasiswa);

        cariBeasiswa = view.findViewById(R.id.cari_beasiswa);
        cariBeasiswa.setQueryHint("Cari beasiswa...");
        cariBeasiswa.setIconified(false);
        cariBeasiswa.setOnQueryTextListener(this);


        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);
        bSpinner = view.findViewById(R.id.spinner_beasiswa);



        String[] items = getResources().getStringArray(R.array.kategoribeasiswa);
        List<String> spinnerItems = new ArrayList<String>();

        for (int i = 0; i < items.length; i++) {
            spinnerItems.add(items[i]);
        }

        SpinnerCariAdapter spinneradapter = new SpinnerCariAdapter(((AppCompatActivity) getActivity()), spinnerItems);
        bSpinner.setAdapter(spinneradapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        bSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                switch(position){
                    case 0:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchBeasiswa(position);
                        break;
                    case 1:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchBeasiswa(position);
                        break;
                    case 2:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchBeasiswa(position);
                        break;
                    default:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchBeasiswa(0);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bSpinner.setDropDownVerticalOffset(-116);
        }

        return view;
    }

    public void fetchBeasiswa (Integer kategori){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Beasiswa>> call = apiInterface.getBeasiswa(kategori);
        call.enqueue(new Callback<List<Beasiswa>>() {
            @Override
            public void onResponse(@NonNull Call<List<Beasiswa>> call, @NonNull Response<List<Beasiswa>> response) {
                progressBar.setVisibility(View.GONE);
                beasiswaList = response.body();
                adapter = new AdapterBeasiswa(getActivity(), beasiswaList);
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
            public void onFailure(@NonNull Call<List<Beasiswa>> call, @NonNull Throwable t) {
                Log.e("tesBeasiswaGagal", "Gagal");
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
        Call<Cari> call = apiInterface.searchBeasiswa(newText);
        Log.e("Newtext ", newText);
        call.enqueue(new Callback<Cari>() {
            @Override
            public void onResponse(Call<Cari> call, Response<Cari> response) {
                String value = response.body().getValue();
                Log.e("Value ", value);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")) {
                    fetchCari = response.body().getListBeasiswa();
                    adapter = new AdapterBeasiswa(getActivity(), fetchCari);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
