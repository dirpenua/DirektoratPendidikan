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
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPWUserBaru extends AppCompatActivity {

    private ProgressDialog pDialog;
    @BindView(R.id.passwordbaru) EditText _passwordbaru;
    @BindView(R.id.ulangipasswordbaru) EditText _ulangipasswordbaru;
    @BindView(R.id.btn_ubah) Button _ubahButton;

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;
    public static final String session_status = "session_status";
    Boolean session = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pwuser_baru);

        sharedpreferences = this.getApplicationContext().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getIntent().getStringExtra(TAG_NAMA);
        nipnik = getIntent().getStringExtra(TAG_NIPNIK);
        session = sharedpreferences.getBoolean(session_status, false);
        ButterKnife.bind(this);

        _ubahButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ubah();
            }
        });
    }

    public void ubah() {

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        ubahPasswordUB();
    }

    private void ubahPasswordUB() {
        pDialog = new ProgressDialog(UbahPWUserBaru.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang mengubah...");
        pDialog.setCancelable(false);

        showpDialog();

        String password_baru = _passwordbaru.getText().toString();



        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.resetPassword(nipnik,password_baru);
        Log.d("NIPNIK",nipnik);
        Log.d("onResponse",password_baru);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

                if(response.body().getSuccess() == 1) {
                    nama = getIntent().getStringExtra(TAG_NAMA);
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(UbahPWUserBaru.this,centeredText, Toast.LENGTH_LONG).show();

                    // menyimpan login ke session
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(session_status, true);
                    editor.putString(TAG_NAMA, nama);
                    editor.putString(TAG_NIPNIK, nipnik);
                    editor.commit();

                    Intent intent = new Intent(UbahPWUserBaru.this, MainActivity.class);
                    intent.putExtra(TAG_NAMA, nama);
                    intent.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(intent);
                }else {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(UbahPWUserBaru.this,centeredText, Toast.LENGTH_LONG).show();
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
        _ubahButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Mohon isi dengan benar", Toast.LENGTH_LONG).show();
        _ubahButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String password_baru = _passwordbaru.getText().toString();
        String ulpassword_baru = _ulangipasswordbaru.getText().toString();

        if (!password_baru.equals(ulpassword_baru)) {
            _passwordbaru.setError("Password tidak sama");
            requestFocus(_passwordbaru);
            valid = false;
        } else {
            _passwordbaru.setError(null);
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
