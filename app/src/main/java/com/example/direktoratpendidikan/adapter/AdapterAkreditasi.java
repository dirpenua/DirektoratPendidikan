package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.DetailAkreditasi;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Akreditasi;

import java.util.ArrayList;
import java.util.List;

public class AdapterAkreditasi extends RecyclerView.Adapter<AdapterAkreditasi.MyViewHolder> {


    List<Akreditasi> akreditasiList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterAkreditasi(Context activityAkreditasi, List akreditasiList) {
        this.akreditasiList = new ArrayList<>(akreditasiList);
        this.context = activityAkreditasi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_akreditasi, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.judulakreditasi.setText(akreditasiList.get(position).getJudulAk());
        holder.jumlahakreditasi.setText(akreditasiList.get(position).getJumlahAk());
        holder.webview.setText(akreditasiList.get(position).getLinkAk());

    }

    @Override
    public int getItemCount() {
        return akreditasiList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judulakreditasi, jumlahakreditasi, webview;
        public MyViewHolder(final View itemView) {
            super(itemView);
            judulakreditasi = itemView.findViewById(R.id.judulakreditasi);
            jumlahakreditasi = itemView.findViewById(R.id.jumlahakreditasi);
            webview = itemView.findViewById(R.id.linkwebview);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailAkreditasi.class);
                    i.putExtra("judulakreditasi",akreditasiList.get(getAdapterPosition()).getJudulAk());
                    i.putExtra("linkwebview",akreditasiList.get(getAdapterPosition()).getLinkAk());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}

