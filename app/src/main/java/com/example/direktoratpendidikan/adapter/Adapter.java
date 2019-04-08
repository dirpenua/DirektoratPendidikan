package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Agenda;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    List<Agenda> agendaList;
    private Activity activity;

    public Adapter(Activity fragmentAgenda, List agendaList) {
        this.agendaList = agendaList;
        activity = fragmentAgenda;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false);
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(activity
                .LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_agenda, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.agenda_id.setText(agendaList.get(position).getId());
        holder.nama_kegiatan.setText(agendaList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView agenda_id, nama_kegiatan;
        public MyViewHolder(View itemView) {
            super(itemView);
            agenda_id = itemView.findViewById(R.id.agenda_id);
            nama_kegiatan = itemView.findViewById(R.id.nama_kegiatan);
        }
    }
}