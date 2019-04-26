package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;

public class AdapterAkun extends BaseAdapter {
    private String[] namapengaturan;
    private Integer[] logoakun;
    private Context context;

    public AdapterAkun(Context fragmentAkun, String[] pengaturan, Integer[] logoakun) {
        this.namapengaturan = pengaturan;
        this.logoakun = logoakun;
        this.context = fragmentAkun;
    }

    @Override
    public int getCount() {
        return namapengaturan.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context
                .LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_akun, null);

        TextView nama_pengaturan = (TextView) v.findViewById(R.id.namapengaturan);
        ImageView logo_akun = (ImageView) v.findViewById(R.id.logoakun);

        // tampilkan data pada komponen
        nama_pengaturan.setText(namapengaturan[i]);
        logo_akun.setImageResource(logoakun[i]);

        return v;
    }
}
