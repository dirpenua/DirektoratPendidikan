package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Agenda;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    List<Agenda> agendaList;
    private Context context;
    private OnItemClickListener mItemClickListener;


    public Adapter(Context fragmentAgenda, List agendaList) {
        this.agendaList = new ArrayList<>(agendaList);
        this.context = fragmentAgenda;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tanggal.setText(agendaList.get(position).getTanggal());
        holder.nama_kegiatan.setText(agendaList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, nama_kegiatan, agenda_id;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            nama_kegiatan = itemView.findViewById(R.id.nama_kegiatan);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}

