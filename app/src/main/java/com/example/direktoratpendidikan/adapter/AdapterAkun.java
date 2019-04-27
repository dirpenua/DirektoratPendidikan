package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.pengaturan.ChangePassword;
import com.example.direktoratpendidikan.pengaturan.EmailVerifikasiActivity;
import com.example.direktoratpendidikan.pengaturan.ProfilActivity;

public class AdapterAkun extends BaseAdapter {
    private String[] namapengaturan;
    private Integer[] logoakun;
    private Context context;
    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;


    public AdapterAkun(Context fragmentAkun, String[] pengaturan, Integer[] logoakun) {
        this.namapengaturan = pengaturan;
        this.logoakun = logoakun;
        this.context = fragmentAkun;
    }

    @Override
    public int getCount() {
        return namapengaturan.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context
                .LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_akun, null);

        TextView nama_pengaturan = (TextView) v.findViewById(R.id.namapengaturan);
        ImageView logo_akun = (ImageView) v.findViewById(R.id.logoakun);

        // tampilkan data pada komponen
        nama_pengaturan.setText(namapengaturan[i]);
        logo_akun.setImageResource(logoakun[i]);

        sharedpreferences = ((Activity) context).getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = ((Activity) context).getIntent().getStringExtra(TAG_NAMA);
        nipnik = ((Activity) context).getIntent().getStringExtra(TAG_NIPNIK);

       v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(i){
                    case 0:
                        Intent profil = new Intent(v.getContext(), ProfilActivity.class);
                        profil.putExtra(TAG_NAMA, nama);
                        profil.putExtra(TAG_NIPNIK, nipnik);
                        v.getContext().startActivity(profil);
                    break;
                    case 1:
                        Intent email = new Intent(v.getContext(), EmailVerifikasiActivity.class);
                        email.putExtra(TAG_NAMA, nama);
                        email.putExtra(TAG_NIPNIK, nipnik);
                        v.getContext().startActivity(email);
                    break;
                    case 2:
                        Intent password = new Intent(v.getContext(), ChangePassword.class);
                        password.putExtra(TAG_NAMA, nama);
                        password.putExtra(TAG_NIPNIK, nipnik);
                        v.getContext().startActivity(password);
                    break;
                    case 3:

                    break;
                    case 4:

                    break;
                    case 5:

                    break;
                }
            }
        });

        return v;
    }
}
