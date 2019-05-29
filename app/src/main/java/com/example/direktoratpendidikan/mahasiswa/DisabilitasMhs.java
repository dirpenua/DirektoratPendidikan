package com.example.direktoratpendidikan.mahasiswa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.admin.DisabilitasAdm;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisabilitasMhs extends Activity {

    private ProgressDialog pDialog;
    TextView _daftarrelawan;
    EditText _namalengkap, _namapanggil, _nim, _alamatrumah, _alamatkosan, _asalkota, _asalprovinsi, _nohp, _fakultas, _semester, _prodi, _ipk, _prestasi;
    CheckBox databenar, satutahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabilitas_mhs);
        _namalengkap = findViewById(R.id.namalengkaprelawan);
        _namapanggil = findViewById(R.id.namapanggilanrelawan);
        _nim = findViewById(R.id.nimrelawan);
        _alamatrumah = findViewById(R.id.alamatrumahrelawan);
        _alamatkosan = findViewById(R.id.alamatkosanrelawan);
        _asalkota = findViewById(R.id.asalkotarelawan);
        _asalprovinsi = findViewById(R.id.asalprovrelawan);
        _nohp =  findViewById(R.id.nohprelawan);
        _fakultas = findViewById(R.id.fakultasrelawan);
        _semester = findViewById(R.id.semesterrelawan);
        _prodi = findViewById(R.id.prodirelawan);
        _ipk = findViewById(R.id.ipkrelawan);
        _prestasi = findViewById(R.id.prestasirelawan);
        _daftarrelawan = findViewById(R.id.daftarrelawan);

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

    public void removeSplash(View view){
        setContentView(R.layout.activity_disabilitas_mhs);
    }

    private void daftarRelawan() {
        pDialog = new ProgressDialog(DisabilitasMhs.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang memproses pendaftaran...");
        pDialog.setCancelable(false);

        showpDialog();

        _namalengkap = findViewById(R.id.namalengkaprelawan);
        _namapanggil = findViewById(R.id.namapanggilanrelawan);
        _nim = findViewById(R.id.nimrelawan);
        _alamatrumah = findViewById(R.id.alamatrumahrelawan);
        _alamatkosan = findViewById(R.id.alamatkosanrelawan);
        _asalkota = findViewById(R.id.asalkotarelawan);
        _asalprovinsi = findViewById(R.id.asalprovrelawan);
        _nohp = findViewById(R.id.nohprelawan);
        _fakultas = findViewById(R.id.fakultasrelawan);
        _semester = findViewById(R.id.semesterrelawan);
        _prodi = findViewById(R.id.prodirelawan);
        _ipk = findViewById(R.id.ipkrelawan);
        _prestasi = findViewById(R.id.prestasirelawan);

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

        Log.d("nama lengkap", namalengkap);
        Log.d("nama panggil", namapanggil);


        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.daftarRelawan(nim, namalengkap,namapanggil, alamatrumah, alamatkosan, asalkota, asalprovinsi, nohp, fakultas, prodi, semester, ipk, prestasi);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                Log.d("SUKSESNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                if(response.body().getSuccess() == 1) {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
                }else{
                    String text = response.body().getMessage();
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

        if (namalengkap.isEmpty()) {
            _namalengkap.setError("Nama lengkap harus diisi");
            requestFocus(_namalengkap);
            valid = false;
        } else {
            _namalengkap.setError(null);
        }

        if (namapanggil.isEmpty()) {
            _namapanggil.setError("Nama panggil harus diisi");
            requestFocus(_namapanggil);
            valid = false;
        } else {
            _namapanggil.setError(null);
        }

        if (nim.isEmpty()) {
            _nim.setError("NIM harus diisi");
            requestFocus(_nim);
            valid = false;
        } else {
            _nim.setError(null);
        }


        if (alamatrumah.isEmpty()) {
            _alamatrumah.setError("Alamat rumah harus diisi");
            requestFocus(_alamatrumah);
            valid = false;
        } else {
            _alamatrumah.setError(null);
        }

        if (asalkota.isEmpty()) {
            _asalkota.setError("Asal kota harus diisi");
            requestFocus(_asalkota);
            valid = false;
        } else {
            _asalkota.setError(null);
        }

        if (asalprovinsi.isEmpty()) {
            _asalprovinsi.setError("Asal provinsi harus diisi");
            requestFocus(_asalprovinsi);
            valid = false;
        } else {
            _asalprovinsi.setError(null);
        }

        if (nohp.isEmpty()) {
            _nohp.setError("No HP harus diisi");
            requestFocus(_nohp);
            valid = false;
        } else {
            _nohp.setError(null);
        }

        if (fakultas.isEmpty()) {
            _fakultas.setError("Fakultas harus diisi");
            requestFocus(_fakultas);
            valid = false;
        } else {
            _fakultas.setError(null);
        }

        if (semester.isEmpty()) {
            _semester.setError("Semester harus diisi");
            requestFocus(_semester);
            valid = false;
        } else {
            _semester.setError(null);
        }

        if (prodi.isEmpty()) {
            _prodi.setError("Program studi harus diisi");
            requestFocus(_prodi);
            valid = false;
        } else {
            _prodi.setError(null);
        }

        if (ipk.isEmpty()) {
            _ipk.setError("IPK harus diisi");
            requestFocus(_ipk);
            valid = false;
        } else {
            _ipk.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
