package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailBeasiswa;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.Prosedur;

import java.util.ArrayList;
import java.util.List;

public class AdapterProsedur extends RecyclerView.Adapter<AdapterProsedur.MyViewHolder> {
    List<Prosedur> prosedurList;
    private Context context;
    private Adapter.OnItemClickListener mItemClickListener;

    public AdapterProsedur(Context fragmentProsedur, List prosedurList) {
        this.prosedurList = new ArrayList<>(prosedurList);
        this.context = fragmentProsedur;
    }

    @Override
    public AdapterProsedur.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prosedur, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AdapterProsedur.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterProsedur.MyViewHolder holder, int position) {
        holder.idprosedur.setText(prosedurList.get(position).getIdPro());
        holder.judulprosedur.setText(prosedurList.get(position).getJudul());
        holder.konten_prosedur.setText(prosedurList.get(position).getKontenPendek());
        holder.kontenpanjangpro.setText(prosedurList.get(position).getKontenPanjang());
        holder.tglpublish.setText(prosedurList.get(position).getTanggal());


    }

    @Override
    public int getItemCount() {
        return prosedurList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idprosedur, judulprosedur, konten_prosedur, kontenpanjangpro, tglpublish;

        public MyViewHolder(final View itemView) {
            super(itemView);
            idprosedur = itemView.findViewById(R.id.hideidpro);
            judulprosedur = itemView.findViewById(R.id.judulprosedur);
            konten_prosedur = itemView.findViewById(R.id.kontenpendekpro);
            kontenpanjangpro = itemView.findViewById(R.id.hidekontenpro);
            tglpublish = itemView.findViewById(R.id.hidetglpro);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent i = new Intent(v.getContext(), DetailBeasiswa.class); //Detail Beasiswa
//                    i.putExtra("idbeasiswa",prosedurList.get(getAdapterPosition()).getIdPro());
//                    i.putExtra("judulbeasiswa",prosedurList.get(getAdapterPosition()).getJudul());
//                    i.putExtra("kontenpanjangbeasiswa",prosedurList.get(getAdapterPosition()).getKontenPanjang());
//                    i.putExtra("tglpublish",prosedurList.get(getAdapterPosition()).getTanggal());
//                    v.getContext().startActivity(i);
                }

            });
        }
    }
}
