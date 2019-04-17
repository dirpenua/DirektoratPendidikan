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
        holder.jammulai.setText(agendaList.get(position).getJammulai());
        holder.jamselesai.setText(agendaList.get(position).getJamselesai());
        holder.nama_kegiatan.setText(agendaList.get(position).getNama());
        holder.bulantahun.setText(agendaList.get(position).getBulantahun());
        holder.tempat.setText(agendaList.get(position).getTempat());
        holder.jumlahundangan.setText(agendaList.get(position).getJumlahUndangan());
        holder.narahubung.setText(agendaList.get(position).getNarahubung());

    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, jammulai, jamselesai, nama_kegiatan, tempat, jumlahundangan, narahubung, bulantahun;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            jammulai = itemView.findViewById(R.id.jmulai);
            jamselesai = itemView.findViewById(R.id.jselesai);
            nama_kegiatan = itemView.findViewById(R.id.nama_kegiatan);
            bulantahun = itemView.findViewById(R.id.bultahun);
            tempat = itemView.findViewById(R.id.tempat);
            jumlahundangan = itemView.findViewById(R.id.jumundangan);
            narahubung = itemView.findViewById(R.id.narahubung);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailAgenda.class);
                    i.putExtra("tanggalKegiatan",agendaList.get(getAdapterPosition()).getTanggal());
                    i.putExtra("jamMulai",agendaList.get(getAdapterPosition()).getJammulai());
                    i.putExtra("jamSelesai",agendaList.get(getAdapterPosition()).getJamselesai());
                    i.putExtra("bulanTahun",agendaList.get(getAdapterPosition()).getBulantahun());
                    i.putExtra("namaKegiatan",agendaList.get(getAdapterPosition()).getNama());
                    i.putExtra("tempatKegiatan",agendaList.get(getAdapterPosition()).getTempat());
                    i.putExtra("jumlahUndangan",agendaList.get(getAdapterPosition()).getJumlahUndangan());
                    i.putExtra("nohpNarahubung",agendaList.get(getAdapterPosition()).getNarahubung());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}

