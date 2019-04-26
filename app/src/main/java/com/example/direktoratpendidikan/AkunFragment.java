package com.example.direktoratpendidikan;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.direktoratpendidikan.adapter.AdapterAkun;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;
    TextView _namauser,_nipnik;
    Button btnLogout;
    private String[] nama_pengaturan = {"Profil", "Email verifikasi", "Ubah password", "Bantuan", "Tentang DirpenUA"};
    private Integer[] logo = {R.drawable.ic_akun, R.drawable.ic_email, R.drawable.ic_password, R.drawable.ic_bantuan, R.drawable.ic_tentangaplikasi};
    private ListView lvakun;

    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);


        lvakun = (ListView) view.findViewById(R.id.lvakun);

        AdapterAkun adapter = new AdapterAkun(getActivity(), nama_pengaturan, logo);
        lvakun.setAdapter(adapter);

        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getActivity().getIntent().getStringExtra(TAG_NAMA);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);


        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */

        _namauser = view.findViewById(R.id.akun_namauser);
        _nipnik = view.findViewById(R.id.akun_nipnik);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);

        _namauser.setText(nama);
        _nipnik.setText(nipnik);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_NAMA, null);
                editor.putString(TAG_NIPNIK, null);
                editor.commit();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return view;
    }

}
