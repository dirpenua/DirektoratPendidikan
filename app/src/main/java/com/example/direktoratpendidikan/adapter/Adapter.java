package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Data;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewProcessHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Data> item; //memanggil modelData

    public Adapter(Activity activity, ArrayList<Data> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(ViewProcessHolder holder, int position) {

        final Data data = item.get(position);
        holder.nama_kegiatan.setText(data.getNama());//menampilkan data
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        TextView nama_kegiatan;

        public ViewProcessHolder(View view) {
            super(view);
            nama_kegiatan = (TextView) view.findViewById(R.id.nama_acara);
        }
    }
}