package com.example.direktoratpendidikan.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.direktoratpendidikan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunAdminFragment extends Fragment {


    public AkunAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun_admin, container, false);
    }

}
