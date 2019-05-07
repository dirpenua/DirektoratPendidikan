package com.example.direktoratpendidikan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAgendaAdmin extends AppCompatActivity {

    public String agenda_id, nohpdb;
    private TextView tgllengkap, tanggal_kegiatan, jam_mulai, jam_selesai, nama_kegiatan, bulantahun, tempat, jumlahundangan, narahubung, hidenarahubung;
    public ImageView onback, tambahpeserta, edit, close;
    private SwipeRefreshLayout swipeContainer;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    Dialog dUbahAgenda;
    private ProgressDialog pDialog;
    TextView _ubahagenda;
    EditText _namakegiatan, _tempat, _nohp, _tglmulai, _jammulai, _jamselesai;
    DatePickerDialog.OnDateSetListener tglmulai;
    Calendar myAgenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda_admin);

        progressBar = findViewById(R.id.prograss);
        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit = (ImageView) findViewById(R.id.agendaedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dUbahAgenda = new Dialog(DetailAgendaAdmin.this);
                dUbahAgenda.setContentView(R.layout.ubahagenda);

                close = dUbahAgenda.findViewById(R.id.dialogclose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dUbahAgenda.dismiss();
                    }
                });

                _namakegiatan = dUbahAgenda.findViewById(R.id.unamakegiatan);
                _tempat = dUbahAgenda.findViewById(R.id.utempat);
                _nohp = dUbahAgenda.findViewById(R.id.unohp);
                _tglmulai = dUbahAgenda.findViewById(R.id.utglmulai);
                _jammulai = dUbahAgenda.findViewById(R.id.ujammulai);
                _jamselesai = dUbahAgenda.findViewById(R.id.ujamselesai);
                _ubahagenda = dUbahAgenda.findViewById(R.id.ubahagenda);

                //Mengedit dari belakang typing (delete) untuk yang sudah ada
                _namakegiatan.setText(nama_kegiatan.getText());
                Editable nama = _namakegiatan.getText();
                Selection.setSelection(nama,_namakegiatan.getText().toString().length());

                _tempat.setText(tempat.getText());
                Editable tempat = _tempat.getText();
                Selection.setSelection(tempat,_tempat.getText().toString().length());

                nohpdb = hidenarahubung.getText().toString();
                _nohp.setText(nohpdb);


                _tglmulai.setText(tgllengkap.getText());
                _jammulai.setText(jam_mulai.getText());
                _jamselesai.setText(jam_selesai.getText());





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

                myAgenda = Calendar.getInstance();
                tglmulai = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myAgenda.set(Calendar.YEAR, year);
                        myAgenda.set(Calendar.MONTH, monthOfYear);
                        myAgenda.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelMulai();
                    }
                };


                _tglmulai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(v.getContext(), tglmulai, myAgenda
                                .get(Calendar.YEAR), myAgenda.get(Calendar.MONTH),
                                myAgenda.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });




                _jammulai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //_jammulai.setText(selectedHour + ":" + selectedMinute);
                                _jammulai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });

                _jamselesai.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //_jamselesai.setText(selectedHour + ":" + selectedMinute);
                                _jamselesai.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });

//                _buatagenda.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(),_tglmulai.getText().toString() +" "+_jammulai.getText().toString() +":00" +"\n" +_tglselesai.getText().toString() +" "+ _jamselesai.getText().toString() +":00", Toast.LENGTH_SHORT).show();
//                    }
//                });

                _ubahagenda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate() == false) {
                            onSimpanFailed();
                            return;
                        }
                        ubahAgenda();

                   }
                });
                dUbahAgenda.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dUbahAgenda.show();
            }
        });

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_bright);





        tanggal_kegiatan = findViewById(R.id.tanggaldetail);
        tgllengkap = findViewById(R.id.hidetgllengkap);
        nama_kegiatan = findViewById(R.id.nama_kegiatan_detail);
        jam_mulai = findViewById(R.id.jammulai);
        jam_selesai = findViewById(R.id.jamselesai);
        bulantahun = findViewById(R.id.bulantahun);
        tempat = findViewById(R.id.tempatdetail);
        jumlahundangan = findViewById(R.id.jumlahundangan);
        narahubung = findViewById(R.id.narahubungdetail);
        hidenarahubung = findViewById(R.id.hideubahnohp);
        tambahpeserta = findViewById(R.id.tambahpeserta);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            final String idagenda =(String) b.get("idagenda");
            fetchDetailAgenda(idagenda);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchDetailAgenda(idagenda);
                }
            });
            tambahpeserta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), TambahPeserta.class);
                    i.putExtra("idagenda",idagenda);
                    startActivity(i);
                }
            });

        }




    }

    public void fetchDetailAgenda (String idagenda){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Agenda> userCall = apiInterface.getDetailAgenda(idagenda);
        userCall.enqueue(new Callback<Agenda>() {
            @Override
            public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                //Log.d("onResponse", "" + response.body().getMessage());
                progressBar.setVisibility(View.GONE);
                tgllengkap.setText(response.body().getTanggalLengkap());
                tanggal_kegiatan.setText(response.body().getTanggal());
                jam_mulai.setText(response.body().getJammulai());
                jam_selesai.setText(response.body().getJamselesai());
                bulantahun.setText(response.body().getBulantahun());
                nama_kegiatan.setText(response.body().getNama());
                tempat.setText(response.body().getTempat());
                jumlahundangan.setText(response.body().getJumlahUndangan());
                hidenarahubung.setText(response.body().getNarahubung());
                final String nrhb =response.body().getNarahubung();
                if(nrhb.length() == 0 || nrhb == null){
                    narahubung.setText("Narahubung tidak tersedia");
                }
                else{
                    narahubung.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String url = "https://wa.me/62" + nrhb;
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(v.getContext(), "Nomor ini tidak terhubung dengan akun WhatsApp manapun",  Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
                    narahubung.setText("0"+nrhb);
                    narahubung.setTextIsSelectable(true);
                }

            }

            @Override
            public void onFailure(Call<Agenda> call, Throwable t) {
                Log.d("onFailure", t.toString());
                String text = "Koneksi sedang tidak stabil. Refresh halaman atau tunggu beberepa saat";
                SpannableString centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateLabelMulai() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _tglmulai.setText(sdf.format(myAgenda.getTime()));
    }

    private void ubahAgenda() {
        pDialog = new ProgressDialog(DetailAgendaAdmin.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang membuat agenda...");
        pDialog.setCancelable(false);

        showpDialog();

        final String agendaid = getIntent().getStringExtra("idagenda");
        String namakegiatan = _namakegiatan.getText().toString();
        String tempatkegiatan = _tempat.getText().toString();
        String narahubung = _nohp.getText().toString();
        String tgljammulai = _tglmulai.getText().toString() +" "+_jammulai.getText().toString() +":00";
        String tgljamselesai = _tglmulai.getText().toString() +" "+_jamselesai.getText().toString() +":00";

        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.ubahAgenda(agendaid, namakegiatan,tempatkegiatan,narahubung,tgljammulai,tgljamselesai);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                Log.d("SUKSERNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                if(response.body().getSuccess() == 1) {
                    dUbahAgenda.dismiss();
                    fetchDetailAgenda(agendaid);
//                    i.putExtra("judulakreditasi",akreditasiList.get(getAdapterPosition()).getJudulAk());
//                    i.putExtra("linkwebview",akreditasiList.get(getAdapterPosition()).getLinkAk());

                    //NANTI KASIH REFRESH ADAPTER DISINI YAAAAA
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
                String text = "Agenda gagal dibuat. Harap hubungi admin melalui menu BANTUAN";
                Spannable centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onSimpanFailed() {
        Toast.makeText(getApplicationContext(), "Mohon isi dengan benar", Toast.LENGTH_LONG).show();
        _ubahagenda.setEnabled(true);
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

        String namakegiatan = _namakegiatan.getText().toString();
        String tempatkegiatan = _tempat.getText().toString();
        String tglmulai = _tglmulai.getText().toString();
        String jammulai =_jammulai.getText().toString();
        String jamselesai = _jamselesai.getText().toString();

        if (namakegiatan.isEmpty()) {
            _namakegiatan.setError("Nama kegiatan harus diisi");
            requestFocus(_namakegiatan);
            valid = false;
        } else {
            _namakegiatan.setError(null);
        }

        if (tempatkegiatan.isEmpty()) {
            _tempat.setError("Tempat kegiatan harus diisi");
            requestFocus(_tempat);
            valid = false;
        } else {
            _tempat.setError(null);
        }

        if (tglmulai.isEmpty()) {
            _tglmulai.setError("Tanggal mulai harus diisi");
            requestFocus(_tglmulai);
            valid = false;
        } else {
            _tglmulai.setError(null);
        }


        if (jammulai.isEmpty()) {
            _jammulai.setError("Jam mulai harus diisi");
            requestFocus(_jammulai);
            valid = false;
        } else {
            _jammulai.setError(null);
        }

        if (jamselesai.isEmpty()) {
            _jamselesai.setError("Jam selesai harus diisi");
            requestFocus(_jamselesai);
            valid = false;
        } else {
            _jamselesai.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            dUbahAgenda.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
