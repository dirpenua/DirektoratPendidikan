package com.example.direktoratpendidikan.mahasiswa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.AkunFragment;
import com.example.direktoratpendidikan.BeasiswaFragment;
import com.example.direktoratpendidikan.BerandaFragment;
import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.ProsedurFragment;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.data.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityMhs extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_FOTO = "foto_user";
    public final static String TAG_NIPNIK = "nipnik";
    ImageView _hidefotoprofil;
    TextView _hidenamauser;
    String nama, foto, nipnik;
    private ApiInterface apiInterface;
    public static final String CHANNEL_ID = "DirpenUA";
    private static final String CHANNEL_NAME = "Reminder Agenda";
    private static final String CHANNEL_DESC = "Reminder Agenda Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mhs);
        sharedpreferences = this.getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = this.getIntent().getStringExtra(TAG_NAMA);
        foto = this.getIntent().getStringExtra(TAG_FOTO);
        nipnik = this.getIntent().getStringExtra(TAG_NIPNIK);

        _hidenamauser = findViewById(R.id.hide_namauser);
        _hidefotoprofil = findViewById(R.id.hidefotoprofil);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//        FirebaseMessaging.getInstance().subscribeToTopic("sendmessage");
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Log.e("newToken", newToken);
//
//            }
//        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            saveToken(token);
                        } else {

                        }
                    }
                });

        // kita set default nya Home Fragment
        loadFragment(new BerandaFragment());

        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_mhs);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void fetchAkun (String nipnik){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Profil> userCall = apiInterface.userPic(nipnik);
        Log.d("nipnik",nipnik);
        userCall.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(Call<Profil> call, Response<Profil> response) {
                //Log.d("onResponse", "" + response.body().getMessage());
                Log.d("Nama", "" + response.body().getNamaProfil());
                Log.d("Foto", "" + response.body().getFotoProfil());
                String namadb = response.body().getNamaProfil();
                String fotoprofil = response.body().getFotoProfil();
                _hidenamauser.setText(namadb);
                Picasso.with(getApplicationContext()).load(ApiClient.USER_PIC+fotoprofil).error(R.drawable.userpic).into(_hidefotoprofil);

            }

            @Override
            public void onFailure(Call<Profil> call, Throwable t) {
                Log.d("onFailure", t.toString());
                String text = "Sistem sedang bermasalah menampilkan halaman profil. Harap laporkan pada admin ";
                SpannableString centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveToken(String token){
        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Notif> userCall = service.saveToken(nipnik, token);
        Log.d("NIPNIK",nipnik);
        Log.d("Token",token);
        userCall.enqueue(new Callback<Notif>() {
            @Override
            public void onResponse(Call<Notif> call, Response<Notif> response) {

                if(response.body().getSuccess() == 1) {
                    String text = "Selamat datang kembali";
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(MainActivityMhs.this,centeredText, Toast.LENGTH_LONG).show();

                }else {

                }
            }

            @Override
            public void onFailure(Call<Notif> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }


    // method untuk load fragment yang sesuai
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_containermhs, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    // method listener untuk logika pemilihan
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.beranda:
                fragment = new BerandaFragment();
                break;
            case R.id.prosedur:
                fragment = new ProsedurFragment();
                break;
            case R.id.faq: //ganti dengan FAQ
                fragment = new FaqFragment();
                break;
            case R.id.beasiswa:
                fragment = new BeasiswaFragment();
                break;
            case R.id.akun:
                fragment = new AkunFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
