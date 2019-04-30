package com.example.direktoratpendidikan;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.adapter.AdapterProsedur;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
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
public class ProsedurFragment extends Fragment {


    public ProsedurFragment() {
        // Required empty public constructor
    }

    private SearchView cariProsedur;
    private Spinner pSpinner;
    FragmentActivity mActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List prosedurList;
    private AdapterProsedur adapterpro;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

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
        View view = inflater.inflate(R.layout.fragment_prosedur, container, false);

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerViewProsedur);

        cariProsedur = view.findViewById(R.id.cari_prosedur);
        cariProsedur.setQueryHint("Cari prosedur...");
        cariProsedur.setIconified(false);

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
                        break;
                    case 1:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        progressBar.setVisibility(View.VISIBLE);
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


}
