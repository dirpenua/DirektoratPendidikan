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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment{

    private SwipeRefreshLayout swipeContainer;
    private Toolbar mToolbar;
    private Spinner mSpinner;
    FragmentActivity mActivity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List agendaList;
    private Adapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerView);

        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);
        mSpinner = view.findViewById(R.id.spinner_rss);
        mToolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        String[] items = getResources().getStringArray(R.array.hari);
        List<String> spinnerItems = new ArrayList<String>();

        for (int i = 0; i < items.length; i++) {
            spinnerItems.add(items[i]);
        }

        SpinnerAdapter spinneradapter = new SpinnerAdapter(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext(), spinnerItems);
        mSpinner.setAdapter(spinneradapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                                                android.R.color.holo_blue_light,
                                                android.R.color.holo_blue_bright);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                final Intent intent;
                switch(position){
                    case 0:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "monday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "monday", nipnik);
                            }
                        });
                        break;
                    case 1:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "tuesday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "tuesday", nipnik);
                            }
                        });
                        break;
                    case 2:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "wednesday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "wednesday", nipnik);
                            }
                        });
                        break;
                    case 3:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "thursday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "thursday", nipnik);
                            }
                        });
                        break;
                    case 4:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "friday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "friday", nipnik);
                            }
                        });
                        break;
                    case 5:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "saturday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "saturday", nipnik);
                            }
                        });
                        break;
                    case 6:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgenda("agenda", "sunday", nipnik);
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgenda("agenda", "sunday", nipnik);
                            }
                        });
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mSpinner.setDropDownVerticalOffset(-116);
        }

        return view;
    }

        public void fetchAgenda (String type, String hari, String nipnik){
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<Agenda>> call = apiInterface.getAgenda(type,hari,nipnik);
            Log.e("tipe", type);
            Log.e("hari", hari);
            Log.e("nipnik", nipnik);
            call.enqueue(new Callback<List<Agenda>>() {
                @Override
                public void onResponse(@NonNull Call<List<Agenda>> call, @NonNull Response<List<Agenda>> response) {
                    progressBar.setVisibility(View.GONE);
                    agendaList = response.body();
                    swipeContainer.setRefreshing(false);
                    adapter = new Adapter(getActivity(), agendaList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (adapter.getItemCount() == 0)
                    {
                        Toast.makeText(getContext(), "Tidak ada agenda hari ini",Toast.LENGTH_SHORT).show();
                    }
//                    adapter.SetOnItemClickListener(new Adapter.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(View v , int position) {
//                            Intent i = new Intent(getActivity(), DetailAgenda.class);
//                            i.putExtra("namaKegiatan",);
//                            startActivity(i);
//                        }
//                    });
                    Log.e("tesAgendaBerhasil", new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<List<Agenda>> call, @NonNull Throwable t) {
                    Log.e("tesAgendaGagal", "Gagal");
                    progressBar.setVisibility(View.GONE);
                }
            });
        }


}
