package com.example.direktoratpendidikan.mahasiswa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.direktoratpendidikan.R;

public class DisabilitasMhs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_disabilitas);
    }

    public void removeSplash(View view){
        setContentView(R.layout.activity_disabilitas_mhs);
    }
}
