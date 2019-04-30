package com.example.direktoratpendidikan.admin;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDosen extends Fragment {

    private FloatingActionButton fab;
    ImageView close;
    Dialog dTambahDsn;
    private ProgressDialog pDialog;
    EditText _nipnik;
    EditText _nama;
    TextView _simpan;

    public TabDosen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_dosen, container, false);


        fab = (FloatingActionButton) view.findViewById(R.id.tambahdosen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dTambahDsn = new Dialog(getContext());
                dTambahDsn.setContentView(R.layout.tambahdosen);

                close = dTambahDsn.findViewById(R.id.dialogclose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dTambahDsn.dismiss();
                    }
                });

                _nipnik = dTambahDsn.findViewById(R.id.tnipnik);
                _nama = dTambahDsn.findViewById(R.id.tnamadosen);
                _simpan = dTambahDsn.findViewById(R.id.simpandosen);
                _simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate() == false) {
                            onSimpanFailed();
                            return;
                        }
                        simpanDosen();
                    }
                });
                dTambahDsn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dTambahDsn.show();
            }
        });
        return view;
    }

    private void simpanDosen() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang mendaftarkan dosen...");
        pDialog.setCancelable(false);

        showpDialog();

        String nipnik = _nipnik.getText().toString();
        String nama = _nama.getText().toString();


        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.tambahDosen(nipnik, nama);

        Log.d("nipnik",nipnik);
        Log.d("nama",nama);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                Log.d("SUKSERNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                if(response.body().getSuccess() == 1) {
                    dTambahDsn.dismiss();
                    //NANTI KASIH REFRESH ADAPTER DISINI YAAAAA
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getContext(),centeredText, Toast.LENGTH_LONG).show();
                }else {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getContext(),centeredText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
//                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void onSimpanFailed() {
        Toast.makeText(getContext(), "Mohon isi dengan benar", Toast.LENGTH_LONG).show();
        _simpan.setEnabled(true);
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

        String nipnik = _nipnik.getText().toString();
        String nama = _nama.getText().toString();

        if (nipnik.isEmpty()) {
            _nipnik.setError("Kolom NIP/NIK harus diisi");
            requestFocus(_nipnik);
            valid = false;
        } else {
            _nipnik.setError(null);
        }

        if (nama.isEmpty()) {
            _nama.setError("Kolom nama dan gelar harus diisi");
            requestFocus(_nama);
            valid = false;
        } else {
            _nama.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            dTambahDsn.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
