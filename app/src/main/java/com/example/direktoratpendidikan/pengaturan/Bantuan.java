package com.example.direktoratpendidikan.pengaturan;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;

public class Bantuan extends Activity {

    public ImageView tutup, email, whatsapp, telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        tutup = (ImageView) findViewById(R.id.close);
        email = (ImageView) findViewById(R.id.emailbantuan);
        whatsapp = (ImageView) findViewById(R.id.wabantuan);
        telp = (ImageView) findViewById(R.id.telpbantuan);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] {"dirpenua@gmail.com"});

                try {
                    startActivity(Intent.createChooser(intent, "Email via..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    String text = "Koneksi internet anda sedang lamban, tunggu beberapa saat dan coba lagi";
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText , Toast.LENGTH_SHORT).show();
                }
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "https://wa.me/6282320001918";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(v.getContext(), "Koneksi internet anda sedang lamban, tunggu beberapa saat dan coba lagi",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        final String tel = "(031) 5914042 ext.126/127";
        telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ tel));

                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    String text = "Koneksi internet anda sedang lamban, tunggu beberapa saat dan coba lagi";
                    Spannable centeredText = new SpannableString(text);
                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, text.length() - 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    Toast.makeText(getApplicationContext(),centeredText , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
