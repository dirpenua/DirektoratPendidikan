package com.example.direktoratpendidikan.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.adapter.AdapterTambahPeserta;
import com.example.direktoratpendidikan.api.ApiInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabRelawan extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List relawanList;
    private AdapterTambahPeserta adaptertp;
    private ApiInterface apiInterface;
    ProgressBar progressBar;

    public TabRelawan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_relawan, container, false);
    }

}
