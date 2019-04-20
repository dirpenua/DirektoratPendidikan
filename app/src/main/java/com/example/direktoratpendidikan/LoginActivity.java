package com.example.direktoratpendidikan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog pDialog;

//    SessionManager session;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    Boolean session = false;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;

    @BindView(R.id.nipnik) EditText _nipnik;
    @BindView(R.id.password) EditText _password;
    @BindView(R.id.btn_login) Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        nama = sharedpreferences.getString(TAG_NAMA, null);
        nipnik = sharedpreferences.getString(TAG_NIPNIK, null);
        ButterKnife.bind(this);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_NIPNIK, nipnik);
            finish();
            startActivity(intent);
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
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


                if(response.body().getSuccess() == 1) {
                    String nama = response.body().getNamaUser();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(LoginActivity.this,centeredText, Toast.LENGTH_LONG).show();

                    if(response.body().getStatus() == 0){
                        //Login pertama kali reset paswword
                        Intent intent = new Intent(LoginActivity.this, UbahPWUserBaru.class);
                        intent.putExtra(TAG_NAMA, nama);
                        intent.putExtra(TAG_NIPNIK, nipnik);
                        finish();
                        startActivity(intent);
                    }else{
                        // Sudah pernah login
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(TAG_NAMA, nama);
                        intent.putExtra(TAG_NIPNIK, nipnik);
                        finish();
                        startActivity(intent);

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_NAMA, nama);
                        editor.putString(TAG_NIPNIK, nipnik);
                        editor.commit();

                    }

                }else {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
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
            }
        });
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
            _nipnik.setError("Kolom NIP/NIK harus diisi");
            requestFocus(_nipnik);
            valid = false;
        } else {
            _nipnik.setError(null);
        }

        if (password_user.isEmpty()) {
            _password.setError("Kolom password harus diisi");
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

