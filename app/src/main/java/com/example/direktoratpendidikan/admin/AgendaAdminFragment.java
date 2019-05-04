package com.example.direktoratpendidikan.admin;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.SpinnerAdapter;
import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class  AgendaAdminFragment extends Fragment {

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
    private FloatingActionButton fab;
    ImageView close;
    Dialog dTambahAgenda;
    private ProgressDialog pDialog;
    EditText _nipnik;
    EditText _nama;
    TextView _simpan;
    Calendar myAgenda;

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
        View view = inflater.inflate(R.layout.fragment_agenda_admin, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.tambahagenda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dTambahAgenda = new Dialog(getContext());
                dTambahAgenda.setContentView(R.layout.tambahagenda);

                close = dTambahAgenda.findViewById(R.id.dialogclose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dTambahAgenda.dismiss();
                    }
                });

                _nipnik = dTambahAgenda.findViewById(R.id.tnipnik);
                _nama =dTambahAgenda.findViewById(R.id.tnamadosen);
                _simpan = dTambahAgenda.findViewById(R.id.buatagenda);
//                _simpan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (validate() == false) {
//                            onSimpanFailed();
//                            return;
//                        }
//                        simpanDosen();
//                    }
//                });
                dTambahAgenda.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dTambahAgenda.show();
            }
        });

        progressBar = view.findViewById(R.id.prograss);
        recyclerView = view.findViewById(R.id.recyclerViewAdmin);

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

                switch(position){
                    case 0:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "monday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "monday");
                            }
                        });
                        break;
                    case 1:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "tuesday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "tuesday");
                            }
                        });
                        break;
                    case 2:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "wednesday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "wednesday");

                            }
                        });
                        break;
                    case 3:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "thursday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "thursday");

                            }
                        });
                        break;
                    case 4:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "friday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "friday");

                            }
                        });
                        break;
                    case 5:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "saturday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "saturday");

                            }
                        });
                        break;
                    case 6:
                        progressBar.setVisibility(View.VISIBLE);
                        fetchAgendaAdmin("agendaadmin", "sunday");
                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                fetchAgendaAdmin("agendaadmin", "sunday");

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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSpinner.setDropDownVerticalOffset(-116);
        }

        return view;
    }

    public void fetchAgendaAdmin (String type, String hari){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Agenda>> call = apiInterface.getAgendaAdmin(type,hari);
        Log.e("tipe", type);
        Log.e("hari", hari);
        call.enqueue(new Callback<List<Agenda>>() {
            @Override
            public void onResponse(@NonNull Call<List<Agenda>> call, @NonNull Response<List<Agenda>> response) {
                progressBar.setVisibility(View.GONE);
                agendaList = response.body();
                swipeContainer.setRefreshing(false);
                //agendaList.clear();
                adapter = new Adapter(getActivity(), agendaList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0)
                {
                    Toast.makeText(getContext(), "Tidak ada agenda hari ini",Toast.LENGTH_SHORT).show();
                }
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
