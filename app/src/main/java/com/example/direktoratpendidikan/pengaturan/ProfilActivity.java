package com.example.direktoratpendidikan.pengaturan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;

public class ProfilActivity extends AppCompatActivity {

    public ImageView tutup;
    public EditText namaprofil;
    TextView nipnikprofil;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    public final static Integer TAG_RULE = 0;
    String nama, nipnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tutup = (ImageView) findViewById(R.id.edit_close);
        namaprofil = findViewById(R.id.edit_nama);
        nipnikprofil = findViewById(R.id.akun_nipnik);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nama = getDefaultString(TAG_NAMA, getApplicationContext());
        nipnik = getDefaultString(TAG_NIPNIK, getApplicationContext());

        namaprofil.setText(nama);
        Editable namapr = namaprofil.getText();
        Selection.setSelection(namapr, namaprofil.getText().toString().length());
        nipnikprofil.setText(nipnik);


    }

    public static String getDefaultString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
