package com.example.direktoratpendidikan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.adapter.AdapterNotifikasi;
import com.example.direktoratpendidikan.admin.AgendaAdminFragment;
import com.example.direktoratpendidikan.admin.MainActivityAdmin;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.dosen.MainActivity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity  {

    private SwipeRefreshLayout swipeContainer;
    FragmentActivity mActivity;
    TextView setel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List notifikasiList;
    private AdapterNotifikasi adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    ImageView tutup;
    SharedPreferences sharedpreferences;
    public final static String TAG_NIPNIK = "nipnik";
    String nipnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerViewNotifikasi);
        tutup = findViewById(R.id.kembali);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);

        sharedpreferences = this.getSharedPreferences(MainActivityAdmin.main_shared_preferences, Context.MODE_PRIVATE);
        nipnik = getDefaultString(TAG_NIPNIK, getApplicationContext());
//        setel = findViewById(R.id.notifikasi);
//        setel.setText(nipnik);

        fetchNotifikasi();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNotifikasi();
            }
        });
    }

    public static String getDefaultString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void fetchNotifikasi (){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MSG>> call = apiInterface.getNotifikasi(nipnik);
        call.enqueue(new Callback<List<MSG>>() {
            @Override
            public void onResponse(@NonNull Call<List<MSG>> call, @NonNull Response<List<MSG>> response) {
                progressBar.setVisibility(View.GONE);
                notifikasiList = response.body();
                swipeContainer.setRefreshing(false);
                adapter = new AdapterNotifikasi(getApplicationContext(), notifikasiList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0)
                   {
                       Toast.makeText(getApplicationContext(), "Tidak ada notifikasi",Toast.LENGTH_SHORT).show();
                   }
                Log.e("tesNotifikasiBerhasil", new Gson().toJson(response.body()));
                adapter.SetOnItemClickListener(new AdapterNotifikasi.OnItemClickListener() {

                        @Override
                        public void onItemClick(View v , int position) {
                            AgendaAdminFragment fragment = new AgendaAdminFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fl_containeradmin, fragment);
                            transaction.commit();
//                            Intent i = new Intent(getApplicationContext(), DetailAgenda.class);
//                            i.putExtra("namaKegiatan",);
//                            startActivity(i);
                        }
                    });
            }

            @Override
            public void onFailure(@NonNull Call<List<MSG>> call, @NonNull Throwable t) {
                Log.e("tesNotifikasiGagal", "Gagal");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
