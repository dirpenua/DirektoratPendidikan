package com.example.direktoratpendidikan.mahasiswa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.BerandaFragment;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisabilitasMhs extends Activity {

    private ProgressDialog pDialog;
    @BindView(R.id.namalengkaprelawan) EditText _namalengkap;
    @BindView(R.id.namapanggilanrelawan) EditText _namapanggil;
    @BindView(R.id.nimrelawan) EditText _nim;
    @BindView(R.id.alamatrumahrelawan) EditText _alamatrumah;
    @BindView(R.id.alamatkosanrelawan) EditText _alamatkosan;
    @BindView(R.id.asalkotarelawan) EditText _asalkota;
    @BindView(R.id.asalprovrelawan) EditText _asalprovinsi;
    @BindView(R.id.nohprelawan) EditText _nohp;
    @BindView(R.id.fakultasrelawan) EditText _fakultas;
    @BindView(R.id.semesterrelawan) EditText _semester;
    @BindView(R.id.prodirelawan) EditText _prodi;
    @BindView(R.id.ipkrelawan) EditText _ipk;
    @BindView(R.id.prestasirelawan) EditText _prestasi;
    @BindView(R.id.daftarrelawan) TextView _daftarrelawan;
    @BindView(R.id.databenar) CheckBox _databenar;
    @BindView(R.id.satutahun) CheckBox _satutahun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabilitas_mhs);

        ButterKnife.bind(this);

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

        _daftarrelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() == false) {
                    onDaftarFailed();
                    return;
                }
                daftarRelawan();
            }
        });

    }

    private void daftarRelawan(){
        pDialog = new ProgressDialog(DisabilitasMhs.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang memproses pendaftaran...");
        pDialog.setCancelable(false);

        showpDialog();

        String namalengkap  = _namalengkap.getText().toString();
        String namapanggil  = _namapanggil.getText().toString();
        String nim          = _nim.getText().toString();
        String alamatrumah  = _alamatrumah.getText().toString();
        String alamatkosan  = _alamatkosan.getText().toString();
        String asalkota     = _asalkota.getText().toString();
        String asalprovinsi = _asalprovinsi.getText().toString();
        String nohp         = _nohp.getText().toString();
        String fakultas     = _fakultas.getText().toString();
        String semester     = _semester.getText().toString();
        String prodi        = _prodi.getText().toString();
        String ipk          = _ipk.getText().toString();
        String prestasi     = _prestasi.getText().toString();

        Log.d("namalengkap", namalengkap);
        Log.d("namapanggil", namapanggil);
        Log.d("nim", nim);
        Log.d("alamatrumah", alamatrumah);
        Log.d("alamatkosan", alamatkosan);
        Log.d("asalkota", asalkota);
        Log.d("asalprovinsi", asalprovinsi);
        Log.d("nohp", nohp);
        Log.d("fakultas", fakultas);
        Log.d("semester", semester);
        Log.d("prodi", prodi);
        Log.d("ipk", ipk);
        Log.d("prestasi", prestasi);

        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MSG> userCall = service.daftarRelawan(nim, namalengkap,namapanggil, alamatrumah, alamatkosan, asalkota, asalprovinsi, nohp, fakultas, prodi, semester, ipk, prestasi);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                Log.d("SUKSESNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                if(response.body().getSuccess() == 1) {
                    Intent berhasil = new Intent(getApplicationContext(), DaftarRelawanBerhasil.class);
                    startActivity(berhasil);
                }else{
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                String text = "Pendaftaran relawan gagal. Harap hubungi admin melalui menu BANTUAN";
                Spannable centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onDaftarFailed() {
        Toast.makeText(getApplicationContext(), "Mohon isi dengan benar", Toast.LENGTH_LONG).show();
        _daftarrelawan.setEnabled(true);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String namalengkap = _namalengkap.getText().toString();
        String namapanggil = _namapanggil.getText().toString();
        String nim = _nim.getText().toString();
        String alamatrumah = _alamatrumah.getText().toString();
        String asalkota = _asalkota.getText().toString();
        String asalprovinsi = _asalprovinsi.getText().toString();
        String nohp = _nohp.getText().toString();
        String fakultas = _fakultas.getText().toString();
        String semester = _semester.getText().toString();
        String prodi = _prodi.getText().toString();
        String ipk = _ipk.getText().toString();
        String prestasi = _prestasi.getText().toString();

        if (ipk.isEmpty()) {
            _ipk.setError("IPK harus diisi");
            requestFocus(_ipk);
            valid = false;
        } else {
            _ipk.setError(null);
        }

        if (prodi.isEmpty()) {
            _prodi.setError("Program studi harus diisi");
            requestFocus(_prodi);
            valid = false;
        } else {
            _prodi.setError(null);
        }

        if (semester.isEmpty()) {
            _semester.setError("Semester harus diisi");
            requestFocus(_semester);
            valid = false;
        } else {
            _semester.setError(null);
        }

        if (fakultas.isEmpty()) {
            _fakultas.setError("Fakultas harus diisi");
            requestFocus(_fakultas);
            valid = false;
        } else {
            _fakultas.setError(null);
        }

        if (nohp.isEmpty()) {
            _nohp.setError("No HP harus diisi");
            requestFocus(_nohp);
            valid = false;
        } else {
            _nohp.setError(null);
        }

        if (asalprovinsi.isEmpty()) {
            _asalprovinsi.setError("Asal provinsi harus diisi");
            requestFocus(_asalprovinsi);
            valid = false;
        } else {
            _asalprovinsi.setError(null);
        }

        if (asalkota.isEmpty()) {
            _asalkota.setError("Asal kota harus diisi");
            requestFocus(_asalkota);
            valid = false;
        } else {
            _asalkota.setError(null);
        }

        if (alamatrumah.isEmpty()) {
            _alamatrumah.setError("Alamat rumah harus diisi");
            requestFocus(_alamatrumah);
            valid = false;
        } else {
            _alamatrumah.setError(null);
        }

        if (nim.isEmpty()) {
            _nim.setError("NIM harus diisi");
            requestFocus(_nim);
            valid = false;
        } else {
            _nim.setError(null);
        }


        if (namapanggil.isEmpty()) {
            _namapanggil.setError("Nama panggilan harus diisi");
            requestFocus(_namapanggil);
            valid = false;
        } else {
            _namapanggil.setError(null);
        }


        if (namalengkap.isEmpty()) {
            _namalengkap.setError("Nama lengkap harus diisi");
            requestFocus(_namalengkap);
            valid = false;
        } else {
            _namalengkap.setError(null);
        }


        if (!_databenar.isChecked()) {
            _databenar.setError("");
            requestFocus(_databenar);
            valid = false;
        } else {
            _databenar.setError(null);
        }

        if (!_satutahun.isChecked()) {
            _satutahun.setError("");
            requestFocus(_satutahun);
            valid = false;
        } else {
            _satutahun.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((DisabilitasMhs.this));

            alertDialogBuilder
                    .setMessage("Jika keluar maka data yang sudah diisi akan hilang, anda yakin?" )
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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

        this.doubleBackToExitPressedOnce = true;
    }
}
