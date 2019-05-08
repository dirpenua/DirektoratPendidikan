package com.example.direktoratpendidikan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.adapter.AdapterAkun;
import com.example.direktoratpendidikan.admin.MainActivityAdmin;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Profil;
import com.example.direktoratpendidikan.dosen.MainActivity;
import com.example.direktoratpendidikan.mahasiswa.MainActivityMhs;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {

    SharedPreferences sharedpreferences;
    SharedPreferences akun_sharepref;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_FOTO = "foto_user";
    public final static String TAG_NIPNIK = "nipnik";
    ImageView _fotoprofil;
    String nama, foto, nipnik;
    TextView _namauser,_nipnik;
    Button btnLogout;
    private String[] nama_pengaturan = {"Profil", "Email verifikasi", "Ubah password", "Bantuan", "Tentang DirpenUA"};
    private Integer[] logo = {R.drawable.ic_akun, R.drawable.ic_email, R.drawable.ic_password, R.drawable.ic_bantuan, R.drawable.ic_tentangaplikasi};
    private ListView lvakun;
    private ApiInterface apiInterface;

    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);


        lvakun = (ListView) view.findViewById(R.id.lvakun);

        AdapterAkun adapter = new AdapterAkun(getActivity(), nama_pengaturan, logo);
        lvakun.setAdapter(adapter);
//        lvakun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.getAdap
//            }
//        });


        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getActivity().getIntent().getStringExtra(TAG_NAMA);
        foto = getActivity().getIntent().getStringExtra(TAG_FOTO);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);

        setDefaultString(TAG_NAMA, nama,getContext());
        setDefaultString(TAG_NIPNIK, nipnik,getContext());




        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */

        _namauser = view.findViewById(R.id.akun_namauser);
        _nipnik = view.findViewById(R.id.akun_nipnik);
        _fotoprofil = view.findViewById(R.id.fotoprofil);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);

        _namauser.setText(nama);
        Picasso.with(getContext()).load(ApiClient.USER_PIC+foto).error(R.drawable.userpic).into(_fotoprofil);
        //fetchAkun(nipnik);
        _nipnik.setText(nipnik);




        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                //alertDialogBuilder.setTitle("Logout akun?");

                alertDialogBuilder
                        .setMessage("Anda yakin ingin keluar?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(LoginActivity.session_status, false);
                                editor.putString(TAG_NAMA, null);
                                editor.putString(TAG_FOTO, null);
                                editor.putString(TAG_NIPNIK, null);
                                editor.commit();

                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });

        return view;
    }

//    public void fetchAkun (String nipnik){
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<Profil> userCall = apiInterface.userPic(nipnik);
//        Log.d("nipnik",nipnik);
//        userCall.enqueue(new Callback<Profil>() {
//            @Override
//            public void onResponse(Call<Profil> call, Response<Profil> response) {
//                //Log.d("onResponse", "" + response.body().getMessage());
//                Log.d("Nama", "" + response.body().getNamaProfil());
//                Log.d("Foto", "" + response.body().getFotoProfil());
//                String namadb = response.body().getNamaProfil();
//                String fotoprofil = response.body().getFotoProfil();
//                _namauser.setText(namadb);
//                Picasso.with(getContext()).load(ApiClient.USER_PIC+fotoprofil).error(R.drawable.userpic).into(_fotoprofil);
//
//            }
//
//            @Override
//            public void onFailure(Call<Profil> call, Throwable t) {
//                Log.d("onFailure", t.toString());
//                String text = "Sistem sedang bermasalah menampilkan halaman profil. Harap laporkan pada admin ";
//                SpannableString centeredText = new SpannableString(text);
//                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
//                        0, text.length() - 1,
//                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                Toast.makeText(getContext(),centeredText, Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    public static void setDefaultString(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
