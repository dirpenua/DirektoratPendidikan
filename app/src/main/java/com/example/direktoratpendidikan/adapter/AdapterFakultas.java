package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.DetailBerita;
import com.example.direktoratpendidikan.ProgramStudi;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Fakultas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterFakultas extends RecyclerView.Adapter<AdapterFakultas.MyViewHolder> {
    List<Fakultas> fakultasList;
    private Context context;
    private Adapter.OnItemClickListener mItemClickListener;

    public AdapterFakultas(Context fragmentFakultas, List fakultasList) {
        this.fakultasList = new ArrayList<>(fakultasList);
        this.context = fragmentFakultas;
    }

    @Override
    public AdapterFakultas.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fakultas, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AdapterFakultas.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterFakultas.MyViewHolder holder, int position) {
        holder.namafakultas.setText(fakultasList.get(position).getNamaF());
        Picasso.with(context).load(ApiClient.FAKUL_IMAGE_URL+fakultasList.get(position).getGambarF()).error(R.drawable.ic_progress).into(holder.gambarfakultas);
        holder.apigmaps.setText(fakultasList.get(position).getGmapsF());
        holder.notelp.setText(fakultasList.get(position).getTelpF());
        holder.email.setText(fakultasList.get(position).getEmailF());
        holder.kampusfakultas.setText(fakultasList.get(position).getKampusF());
        holder.alamatfakultas.setText(fakultasList.get(position).getAlamatF());
        holder.faxfakultas.setText(fakultasList.get(position).getFaxF());
        holder.idfakultas.setText(fakultasList.get(position).getIdF());
    }

    @Override
    public int getItemCount() {
        return fakultasList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namafakultas, apigmaps, notelp, email, kampusfakultas, alamatfakultas, faxfakultas, idfakultas;
        ImageView gambarfakultas;

        public MyViewHolder(final View itemView) {
            super(itemView);
            namafakultas = itemView.findViewById(R.id.namafakultas);
            gambarfakultas = itemView.findViewById(R.id.gambarfakultas);
            apigmaps = itemView.findViewById(R.id.hideapigmaps);
            notelp = itemView.findViewById(R.id.hidenotelp);
            email = itemView.findViewById(R.id.hideemail);
            kampusfakultas = itemView.findViewById(R.id.hidekampusfakultas);
            alamatfakultas = itemView.findViewById(R.id.hidealamatfakultas);
            faxfakultas = itemView.findViewById(R.id.hidefaxfakultas);
            idfakultas = itemView.findViewById(R.id.hideidfakultas);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ProgramStudi.class);
                    i.putExtra("idfakultas",fakultasList.get(getAdapterPosition()).getIdF());
                    i.putExtra("namafakultas",fakultasList.get(getAdapterPosition()).getNamaF());
                    i.putExtra("gambarfakultas",fakultasList.get(getAdapterPosition()).getGambarF());
                    i.putExtra("apigmapsfakultas",fakultasList.get(getAdapterPosition()).getGmapsF());
                    i.putExtra("notelpfakultas",fakultasList.get(getAdapterPosition()).getTelpF());
                    i.putExtra("emailfakultas",fakultasList.get(getAdapterPosition()).getEmailF());
                    i.putExtra("kampusfakultas",fakultasList.get(getAdapterPosition()).getKampusF());
                    i.putExtra("alamatfakultas",fakultasList.get(getAdapterPosition()).getAlamatF());
                    i.putExtra("faxfakultas",fakultasList.get(getAdapterPosition()).getFaxF());
                    v.getContext().startActivity(i);
                }

            });
        }
    }
}
