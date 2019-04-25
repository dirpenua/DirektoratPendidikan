package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Agenda;

import java.util.ArrayList;
import java.util.List;

public class AdapterBeasiswa extends RecyclerView.Adapter<AdapterBeasiswa.MyViewHolder> {
    List<Agenda> beasiswaList;
    private Context context;
    private Adapter.OnItemClickListener mItemClickListener;

    public AdapterBeasiswa(Context fragmentBeasiswa, List beasiswaList) {
        this.beasiswaList = new ArrayList<>(beasiswaList);
        this.context = fragmentBeasiswa;
    }

    @Override
    public AdapterBeasiswa.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beasiswa, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AdapterBeasiswa.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterBeasiswa.MyViewHolder holder, int position) {
        holder.judulbeasiswa.setText(beasiswaList.get(position).getTanggal());


    }

    @Override
    public int getItemCount() {
        return beasiswaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judulbeasiswa, konten;

        public MyViewHolder(final View itemView) {
            super(itemView);
            judulbeasiswa = itemView.findViewById(R.id.judulbeasiswa);
            konten = itemView.findViewById(R.id.kontenbeasiswa);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailAgenda.class); //Detail Beasiswa
                    i.putExtra("tanggalKegiatan",beasiswaList.get(getAdapterPosition()).getTanggal());

                    v.getContext().startActivity(i);
                }

            });
        }
    }
}
