package com.example.direktoratpendidikan;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment{

//    private Toolbar mToolbar;
//    private Spinner mSpinner;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List agendaList;
    private Adapter adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;


     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_agenda, container, false);
//         mSpinner = view.findViewById(R.id.spinner_rss);
//         mToolbar = view.findViewById(R.id.toolbar);

//         ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//
//         ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//         mSpinner = view.findViewById(R.id.spinner_rss);
//
//         String[] items = getResources().getStringArray(R.array.hari);
//         List<String> spinnerItems = new ArrayList<String>();
//
//         for (int i = 0; i < items.length; i++) {
//             spinnerItems.add(items[i]);
//         }
//
//         SpinnerAdapter spinneradapter = new SpinnerAdapter(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext(), spinnerItems);
//         mSpinner.setAdapter(spinneradapter);
//
//         if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//             mSpinner.setDropDownVerticalOffset(-116);
//         }

         progressBar = view.findViewById(R.id.prograss);
         recyclerView = view.findViewById(R.id.recyclerView );
         layoutManager = new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setHasFixedSize(true);
         //fetchAgenda("agenda");

         apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
         Call< List< Agenda>> call = apiInterface.getAgenda("agenda");
         call.enqueue(new Callback<List<Agenda>>() {
             @Override
             public void onResponse(@NonNull Call<List<Agenda>> call, @NonNull Response<List<Agenda>> response) {
                 progressBar.setVisibility(View.GONE);
                 agendaList = response.body();
                 adapter = new Adapter(getActivity(), agendaList);
                 recyclerView.setAdapter(adapter);
                 adapter.notifyDataSetChanged();
                 Log.e("tesAgendaBerhasil",new Gson().toJson(response.body()));
             }
             @Override
             public void onFailure(@NonNull Call<List<Agenda>> call, @NonNull Throwable t) {
                 Log.e("tesAgendaGagal","Gagal");
             }
         });

         return view;
     }

//    public void fetchAgenda(String type){
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call< List< Agenda>> call = apiInterface.getAgenda(type);
//        call.enqueue(new Callback<List<Agenda>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Agenda>> call, @NonNull Response<List<Agenda>> response) {
//                progressBar.setVisibility(View.GONE);
//                agendaList = response.body();
//                adapter = new Adapter(agendaList, getActivity());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                Log.e("tesAgendaBerhasil",new Gson().toJson(response.body()));
//            }
//            @Override
//            public void onFailure(@NonNull Call<List<Agenda>> call, @NonNull Throwable t) {
//                Log.e("tesAgendaGagal","Gagal");
//            }
//        });
//    }

}

