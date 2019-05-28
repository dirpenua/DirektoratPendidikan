package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgendaAdmin;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.MyViewHolder> {


    List<MSG> notifikasiList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterNotifikasi(Context actNotifikasi, List notifikasiList) {
        this.notifikasiList = new ArrayList<>(notifikasiList);
        this.context = actNotifikasi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.message.setText(notifikasiList.get(position).getMessage());
        holder.idnotif.setText(notifikasiList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return notifikasiList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message, idnotif;

        public MyViewHolder(final View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.pesannotifikasi);
            idnotif = itemView.findViewById(R.id.hideidnotifikasi);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), DetailAgendaAdmin.class);
                    i.putExtra("idagenda",notifikasiList.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}

