package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.DetailBeasiswa;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Beasiswa;

import java.util.ArrayList;
import java.util.List;

public class AdapterBeasiswa extends RecyclerView.Adapter<AdapterBeasiswa.MyViewHolder> {
    List<Beasiswa> beasiswaList;
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
        holder.idbeasiswa.setText(beasiswaList.get(position).getId());
        holder.judulbeasiswa.setText(beasiswaList.get(position).getJudul());
        holder.kontenbeasiswa.setText(beasiswaList.get(position).getKontenPendek());
        holder.kontenpanjang.setText(beasiswaList.get(position).getKontenPanjang());
        holder.tglpublish.setText(beasiswaList.get(position).getTanggal());


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
        TextView idbeasiswa, judulbeasiswa, kontenbeasiswa, kontenpanjang, tglpublish, baca;

        public MyViewHolder(final View itemView) {
            super(itemView);
            idbeasiswa = itemView.findViewById(R.id.hideid);
            judulbeasiswa = itemView.findViewById(R.id.judulbeasiswa);
            kontenbeasiswa = itemView.findViewById(R.id.kontenpendek);
            kontenpanjang = itemView.findViewById(R.id.hidekonten);
            tglpublish = itemView.findViewById(R.id.hidetgl);
            baca = itemView.findViewById(R.id.baca);
            itemView.setTag(itemView);
            baca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailBeasiswa.class); //Detail Beasiswa
                    i.putExtra("idbeasiswa",beasiswaList.get(getAdapterPosition()).getId());
                    i.putExtra("judulbeasiswa",beasiswaList.get(getAdapterPosition()).getJudul());
                    i.putExtra("kontenpanjangbeasiswa",beasiswaList.get(getAdapterPosition()).getKontenPanjang());
                    i.putExtra("tglpublish",beasiswaList.get(getAdapterPosition()).getTanggal());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}
