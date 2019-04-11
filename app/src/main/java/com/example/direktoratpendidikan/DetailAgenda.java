package com.example.direktoratpendidikan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailAgenda extends AppCompatActivity {

    public String agenda_id;


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);

        this.agenda_id = getIntent().getStringExtra("idAgenda");
    }

    public String getId_agenda(){
        return this.agenda_id;
    }
}
