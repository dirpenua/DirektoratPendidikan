package com.example.direktoratpendidikan.pengaturan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.direktoratpendidikan.dosen.MainActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.admin.MainActivityAdmin;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.mahasiswa.MainActivityMhs;

import java.util.regex.Pattern;

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
    public final static Integer TAG_RULE = 0;
    String nama, nipnik;
    Integer rule;
    public static final String session_status = "session_status";
    Boolean session = false;


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
//        nama = getIntent().getStringExtra(TAG_NAMA);
//        nipnik = getIntent().getStringExtra(TAG_NIPNIK);

        nama = getDefaultString(TAG_NAMA, getApplicationContext());
        nipnik = getDefaultString(TAG_NIPNIK, getApplicationContext());
        rule = getIntent().getIntExtra("TAG_RULE", 0);

        if (session) {
            switch (rule) {
                case 1:
                    Intent rule1 = new Intent(ChangePassword.this, MainActivityAdmin.class);
                    rule1.putExtra(TAG_NAMA, nama);
                    rule1.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule1);
                    break;
                case 2:
                    Intent rule2 = new Intent(ChangePassword.this, MainActivity.class);
                    rule2.putExtra(TAG_NAMA, nama);
                    rule2.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule2);
                    break;
                case 3:
                    Intent rule3 = new Intent(ChangePassword.this, MainActivityMhs.class);
                    rule3.putExtra(TAG_NAMA, nama);
                    rule3.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(rule3);
                    break;
            }
        }

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change();
            }
        });

    }

    public static String getDefaultString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void change() {

        if (validate() == false) {
            onChangeFailed();
            return;
        }

       changePassword();
    }

    private void changePassword() {
        pDialog = new ProgressDialog(ChangePassword.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sedang mengubah password...");
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
                Log.d("SUKSERNYA", "SUKSESNYA APA: " + response.body().getSuccess());

                switch (response.body().getSuccess()) {
                    case 1:
                    Intent intent = new Intent(ChangePassword.this, MainActivityAdmin.class);
                    intent.putExtra(TAG_NAMA, nama);
                    intent.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(intent);

                    String text = "" + response.body().getMessage();
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
                    break;

                    case 2:
                    intent = new Intent(ChangePassword.this, MainActivity.class);
                    intent.putExtra(TAG_NAMA, nama);
                    intent.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(intent);

                    text = "" + response.body().getMessage();
                    centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
                    break;

                    case 3:
                    intent = new Intent(ChangePassword.this, MainActivityMhs.class);
                    intent.putExtra(TAG_NAMA, nama);
                    intent.putExtra(TAG_NIPNIK, nipnik);
                    finish();
                    startActivity(intent);

                    text = "" + response.body().getMessage();
                    centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();
                    break;

                    default:
                        text = "" + response.body().getMessage();
                        centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, text.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(getApplicationContext(),centeredText, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
//                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void onChangeFailed() {
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

        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (!UpperCasePatten.matcher(password_baru).find()) {
            _passwordbaru.setError("Password harus terdapat huruf besar", null);
            requestFocus(_passwordbaru);
            valid = false;
        } if (!lowerCasePatten.matcher(password_baru).find()) {
            _passwordbaru.setError("Password harus terdapat huruf kecil", null);
            requestFocus(_passwordbaru);
            valid = false;
        } if (!digitCasePatten.matcher(password_baru).find()) {
            _passwordbaru.setError("Password harus terdapat angka", null);
            requestFocus(_passwordbaru);
            valid = false;
        }if (password_baru.length() < 10) {
            _passwordbaru.setError("Password minimal 10 karakter", null);
            requestFocus(_passwordbaru);
            valid = false;
        }if (!password_baru.equals(ulpassword_baru)) {
            _passwordbaru.setError("Password tidak sama", null);
            requestFocus(_passwordbaru);
            valid = false;
        }
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
