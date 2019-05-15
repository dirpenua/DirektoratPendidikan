package com.example.direktoratpendidikan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.direktoratpendidikan.admin.MainActivityAdmin;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.dosen.MainActivity;
import com.example.direktoratpendidikan.mahasiswa.MainActivityMhs;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog pDialog;
    ConnectivityManager conMgr;

//    SessionManager session;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    Boolean session = false;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_FOTO = "foto_user";
    public final static String TAG_NIPNIK = "nipnik";
    public final static Integer TAG_RULE = 0;
    String nama, foto, nipnik;
    int rule;

    @BindView(R.id.nipnik) EditText _nipnik;
    @BindView(R.id.password) EditText _password;
    @BindView(R.id.btn_login) Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(),"Tidak ada koneksi internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        nama = sharedpreferences.getString(TAG_NAMA, null);
        foto = sharedpreferences.getString(TAG_FOTO, null);
        nipnik = sharedpreferences.getString(TAG_NIPNIK, null);
        rule = sharedpreferences.getInt("TAG_RULE", 0);
        ButterKnife.bind(this);

        if (session) {
            switch (rule) {
                case 1:
                    Intent rule1 = new Intent(LoginActivity.this, MainActivityAdmin.class);
                    rule1.putExtra(TAG_NAMA, nama);
                    rule1.putExtra(TAG_FOTO, foto);
                    rule1.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule1);
                    break;
                case 2:
                    Intent rule2 = new Intent(LoginActivity.this, MainActivity.class);
                    rule2.putExtra(TAG_NAMA, nama);
                    rule2.putExtra(TAG_FOTO, foto);
                    rule2.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule2);
                    break;
                case 3:
                    Intent rule3 = new Intent(LoginActivity.this, MainActivityMhs.class);
                    rule3.putExtra(TAG_NAMA, nama);
                    rule3.putExtra(TAG_FOTO, foto);
                    rule3.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule3);
                    break;
            }
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    login();
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        loginByServer();
    }

    private void loginByServer() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Anda sedang login...");
        pDialog.setCancelable(false);

        showpDialog();

        final String nipnik = _nipnik.getText().toString();
        String password_user = _password.getText().toString();


        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.userLogIn(nipnik,password_user);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                Log.d("onResponse", "" + response.body().getMessage());

                switch (response.body().getSuccess()) {
                    case 1:
                        String nama = response.body().getNamaUser();
                        String foto = response.body().getFotoUser();
                        String text = response.body().getMessage();
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();
                            if(response.body().getStatus() == 0){
                                //Login pertama kali reset paswword
                                Intent intent = new Intent(LoginActivity.this, UbahPWUserBaru.class);
                                intent.putExtra(TAG_NAMA, nama);
                                intent.putExtra(TAG_FOTO, foto);
                                intent.putExtra(TAG_NIPNIK, nipnik);
                                finish();
                                startActivity(intent);
                            }else{
                                // Sudah pernah login
                                Intent intent = new Intent(LoginActivity.this, MainActivityAdmin.class);
                                intent.putExtra(TAG_NAMA, nama);
                                intent.putExtra(TAG_FOTO, foto);
                                intent.putExtra(TAG_NIPNIK, nipnik);
                                finish();
                                startActivity(intent);

                                // menyimpan login ke session
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putString(TAG_NAMA, nama);
                                editor.putString(TAG_FOTO, foto);
                                editor.putString(TAG_NIPNIK, nipnik);
                                editor.putInt("TAG_RULE", 1);
                                editor.commit();

                                // menyimpan login ke session dengan default
                                setDefaultBoolean(session_status, true, getApplicationContext());
                                setDefaultString(TAG_NAMA, nama,getApplicationContext());
                                setDefaultString(TAG_FOTO, foto,getApplicationContext());
                                setDefaultString(TAG_NIPNIK, nipnik,getApplicationContext());
                                setDefaultInteger("TAG_RULE",1,getApplicationContext());
                            }
                        break;
                    case 2:
                        nama = response.body().getNamaUser();
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        foto = response.body().getFotoUser();
                        text = response.body().getMessage();
                        centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();
                        if(response.body().getStatus() == 0){
                            //Login pertama kali reset paswword
                            Intent intent = new Intent(LoginActivity.this, UbahPWUserBaru.class);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_FOTO, foto);
                            intent.putExtra(TAG_NIPNIK, nipnik);
                            finish();
                            startActivity(intent);
                        }else{
                            // Sudah pernah login
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_FOTO, foto);
                            intent.putExtra(TAG_NIPNIK, nipnik);
                            finish();
                            startActivity(intent);

                            // menyimpan login ke session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_NAMA, nama);
                            editor.putString(TAG_FOTO, foto);
                            editor.putString(TAG_NIPNIK, nipnik);
                            editor.putInt("TAG_RULE", 2);
                            editor.commit();

                            // menyimpan login ke session dengan default
                            setDefaultBoolean(session_status, true, getApplicationContext());
                            setDefaultString(TAG_NAMA, nama,getApplicationContext());
                            setDefaultString(TAG_FOTO, foto,getApplicationContext());
                            setDefaultString(TAG_NIPNIK, nipnik,getApplicationContext());
                            setDefaultInteger("TAG_RULE",2,getApplicationContext());
                        }
                        break;
                    case 3:
                        nama = response.body().getNamaUser();
                        foto = response.body().getFotoUser();
                        Log.d("Foto user", foto);
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        text = response.body().getMessage();
                        centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();
                        if(response.body().getStatus() == 0){
                            //Login pertama kali reset paswword
                            Intent intent = new Intent(LoginActivity.this, UbahPWUserBaru.class);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_FOTO, foto);
                            intent.putExtra(TAG_NIPNIK, nipnik);
                            finish();
                            startActivity(intent);
                        }else{
                            // Sudah pernah login
                            Intent intent = new Intent(LoginActivity.this, MainActivityMhs.class);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_FOTO, foto);
                            intent.putExtra(TAG_NIPNIK, nipnik);
                            finish();
                            startActivity(intent);

                            // menyimpan login ke session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_NAMA, nama);
                            editor.putString(TAG_FOTO, foto);
                            editor.putString(TAG_NIPNIK, nipnik);
                            editor.putInt("TAG_RULE", 3);
                            editor.commit();

                            // menyimpan login ke session dengan default
                            setDefaultBoolean(session_status, true, getApplicationContext());
                            setDefaultString(TAG_NAMA, nama,getApplicationContext());
                            setDefaultString(TAG_FOTO, foto,getApplicationContext());
                            setDefaultString(TAG_NIPNIK, nipnik,getApplicationContext());
                            setDefaultInteger("TAG_RULE",3,getApplicationContext());
                        }
                        break;
                    default:
                        text = response.body().getMessage();
                        centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
                String text = "Mohon periksa koneksi internet anda";
                SpannableString centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setDefaultString(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setDefaultBoolean(String key, Boolean value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setDefaultInteger(String key, Integer value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login gagal", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String nipnik = _nipnik.getText().toString();
        String password_user = _password.getText().toString();

        if (nipnik.isEmpty()) {
            _nipnik.setError("Kolom NIM/NIP/NIK harus diisi");
            requestFocus(_nipnik);
            valid = false;
        } else {
            _nipnik.setError(null);
        }

        if (password_user.isEmpty()) {
            _password.setError("Kolom password harus diisi",null);
            requestFocus(_password);
            valid = false;
        } else {
            _password.setError(null);
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
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ketuk lagi untuk KEMBALI ", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);

    }
}

