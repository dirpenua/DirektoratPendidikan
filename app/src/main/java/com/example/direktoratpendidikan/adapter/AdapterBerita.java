package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailBeasiswa;
import com.example.direktoratpendidikan.DetailBerita;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Berita;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {
    List<Berita> beritaList;
    private Context context;
    private Adapter.OnItemClickListener mItemClickListener;

    public AdapterBerita(Context fragmentBerita, List beritaList) {
        this.beritaList = new ArrayList<>(beritaList);
        this.context = fragmentBerita;
    }

    @Override
    public AdapterBerita.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AdapterBerita.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterBerita.MyViewHolder holder, int position) {
        holder.judulberita.setText(beritaList.get(position).getJudulB());
        holder.tanggal.setText(beritaList.get(position).getTanggalB());
        holder.bulan.setText(beritaList.get(position).getBulanB());
        holder.kontenberita.setText(beritaList.get(position).getKontenB());
        holder.tglpublish.setText(beritaList.get(position).getTanggalPublishB());
        Picasso.with(context).load(ApiClient.IMAGE_URL+beritaList.get(position).getGambarB()).error(R.drawable.ic_progress).into(holder.gambarberita);


    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judulberita, kontenberita, tanggal, bulan, tglpublish;
        ImageView gambarberita;

        public MyViewHolder(final View itemView) {
            super(itemView);
            judulberita = itemView.findViewById(R.id.judulberita);
            tanggal = itemView.findViewById(R.id.tanggalberita);
            bulan = itemView.findViewById(R.id.bulanberita);
            kontenberita = itemView.findViewById(R.id.hidekonten);
            tglpublish = itemView.findViewById(R.id.hidetgl);
            gambarberita = itemView.findViewById(R.id.gambarberita);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailBerita.class);
                    i.putExtra("judulberita",beritaList.get(getAdapterPosition()).getJudulB());
                    i.putExtra("kontenberita",beritaList.get(getAdapterPosition()).getKontenB());
                    i.putExtra("tglpublish",beritaList.get(getAdapterPosition()).getTanggalPublishB());
                    i.putExtra("gambarberita",beritaList.get(getAdapterPosition()).getGambarB());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}
