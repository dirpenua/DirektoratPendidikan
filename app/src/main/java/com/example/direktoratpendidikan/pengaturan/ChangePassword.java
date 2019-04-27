package com.example.direktoratpendidikan.pengaturan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.AkunFragment;
import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.MainActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.UbahPWUserBaru;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    public ImageView tutup;
    private ProgressDialog pDialog;
    @BindView(R.id.pwlama) EditText _passwordlama;
    @BindView(R.id.pwbaru) EditText _passwordbaru;
    @BindView(R.id.ulangipwbaru) EditText _ulangipasswordbaru;
    @BindView(R.id.edit_save) ImageView save;

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;

    TextView tesnipnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tutup = (ImageView) findViewById(R.id.edit_close);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ButterKnife.bind(this);

        sharedpreferences = this.getSharedPreferences(MainActivity.main_shared_preferences, Context.MODE_PRIVATE);
        nama = getIntent().getStringExtra(TAG_NAMA);
        nipnik = "151611513009";

        tesnipnik =findViewById(R.id.tesnipnik);
        tesnipnik.setText(nipnik); //INI TIDAK BISAAAAAAAAAAAA
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change();
            }
        });

    }

    public void change() {

        if (validate() == false) {
            onLoginFailed();
            return;
        }

       changePassword();
    }

    private void changePassword() {
        pDialog = new ProgressDialog(ChangePassword.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Password sedang diubah...");
        pDialog.setCancelable(false);

        showpDialog();

        String password_lama = _passwordlama.getText().toString();
        String password_baru = _passwordbaru.getText().toString();




        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);

        Call<MSG> userCall = service.changePassword(nipnik, password_baru, password_lama);

        Log.d("nipnik",nipnik);
        Log.d("pwlama",password_lama);
        Log.d("pwbaru",password_baru);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

                if(response.body().getSuccess() == 1) {
                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(ChangePassword.this,centeredText, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ChangePassword.this, AkunFragment.class);
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
                    Toast.makeText(ChangePassword.this,centeredText, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Mohon isi dengan benar", Toast.LENGTH_LONG).show();
        save.setEnabled(true);
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


}
