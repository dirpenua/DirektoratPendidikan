package com.example.direktoratpendidikan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.direktoratpendidikan.admin.MainActivityAdmin;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.mahasiswa.MainActivityMhs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedpreferences;
    public static final String main_shared_preferences= "my_shared_preferences";
    public static final String session_status = "session_status";
    Boolean session = false;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;
    public static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = this.getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        sharedpreferences = getSharedPreferences(main_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        nama = getIntent().getStringExtra(TAG_NAMA);
        nipnik = getIntent().getStringExtra(TAG_NIPNIK);
        ButterKnife.bind(this);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(session_status, true);
        editor.putString(TAG_NAMA, nama);
        editor.putString(TAG_NIPNIK, nipnik);
        editor.commit();

//        if (session) {
//            switch (rule) {
//                case 1:
//                    Intent rule1 = new Intent(LoginActivity.this, MainActivityAdmin.class);
//                    rule1.putExtra(TAG_NAMA, nama);
//                    rule1.putExtra(TAG_NIPNIK, nipnik);
//                    finish();
//                    startActivity(rule1);
//                    break;
//                case 2:
//                    Intent rule2 = new Intent(LoginActivity.this, MainActivity.class);
//                    rule2.putExtra(TAG_NAMA, nama);
//                    rule2.putExtra(TAG_NIPNIK, nipnik);
//                    finish();
//                    startActivity(rule2);
//                    break;
//                case 3:
//                    Intent rule3 = new Intent(LoginActivity.this, MainActivityMhs.class);
//                    rule3.putExtra(TAG_NAMA, nama);
//                    rule3.putExtra(TAG_NIPNIK, nipnik);
//                    finish();
//                    startActivity(rule3);
//                    break;
//            }
//        }

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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
                    Toast.makeText(MainActivity.this,centeredText, Toast.LENGTH_LONG).show();

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
                    .replace(R.id.fl_container, fragment)
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
            case R.id.agenda:
                fragment = new AgendaFragment();
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
