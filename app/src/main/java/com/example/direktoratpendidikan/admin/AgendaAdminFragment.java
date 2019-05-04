package com.example.direktoratpendidikan.admin;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.SpinnerAdapter;
import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    TextView _buatagenda;
    EditText _namakegiatan, _tempat, _jumlahundangan, _nohp, _tglmulai, _tglselesai, _jammulai, _jamselesai;
    DatePickerDialog.OnDateSetListener tglmulai, tglselesai;
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

                _namakegiatan = dTambahAgenda.findViewById(R.id.tnamakegiatan);
                _tempat =dTambahAgenda.findViewById(R.id.ttempat);
                _jumlahundangan = dTambahAgenda.findViewById(R.id.tjumlahundangan);
                _nohp = dTambahAgenda.findViewById(R.id.tnohp);
                _tglmulai = dTambahAgenda.findViewById(R.id.ttglmulai);
                _tglselesai = dTambahAgenda.findViewById(R.id.ttglselesai);
                _jammulai = dTambahAgenda.findViewById(R.id.tjammulai);
                _jamselesai = dTambahAgenda.findViewById(R.id.tjamselesai);
                _buatagenda = dTambahAgenda.findViewById(R.id.buatagenda);



                _nohp.addTextChangedListener(new TextWatcher() {

                    boolean changing = false;
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!changing && _nohp.getText().toString().startsWith("0")){
                            changing = true;
                            _nohp.setText(_nohp.getText().toString().replace("0", ""));
                        }
                        changing = false;
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                });

                myAgenda = Calendar.getInstance();
                tglmulai = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myAgenda.set(Calendar.YEAR, year);
                        myAgenda.set(Calendar.MONTH, monthOfYear);
                        myAgenda.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelMulai();
                    }
                };
                tglselesai = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myAgenda.set(Calendar.YEAR, year);
                        myAgenda.set(Calendar.MONTH, monthOfYear);
                        myAgenda.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelSelesai();
                    }
                };


                _tglmulai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(getContext(), tglmulai, myAgenda
                                .get(Calendar.YEAR), myAgenda.get(Calendar.MONTH),
                                myAgenda.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                _tglselesai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(getContext(), tglselesai, myAgenda
                                .get(Calendar.YEAR), myAgenda.get(Calendar.MONTH),
                                myAgenda.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });



                _jammulai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //_jammulai.setText(selectedHour + ":" + selectedMinute);
                                _jammulai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });

                _jamselesai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //_jamselesai.setText(selectedHour + ":" + selectedMinute);
                                _jamselesai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });

                _buatagenda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),_tglmulai.getText().toString() +" "+_jammulai.getText().toString() +":00" +"\n" +_tglselesai.getText().toString() +" "+ _jamselesai.getText().toString() +":00", Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void updateLabelMulai() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tglmulai.setText(sdf.format(myAgenda.getTime()));
    }

    private void updateLabelSelesai() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tglselesai.setText(sdf.format(myAgenda.getTime()));
    }


}
