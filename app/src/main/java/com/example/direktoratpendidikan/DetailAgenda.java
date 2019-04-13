package com.example.direktoratpendidikan;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.direktoratpendidikan.data.Agenda;

public class DetailAgenda extends AppCompatActivity {

    public String agenda_id;
    public ImageView onback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);
        this.agenda_id = getIntent().getStringExtra("idAgenda");
        onback = (ImageView) findViewById(R.id.agendakembali);
        onback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public String getId_agenda(){
        return this.agenda_id;
    }
}
