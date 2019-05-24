package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.direktoratpendidikan.admin.DetailMBK;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.RelawanMBK;

import java.util.ArrayList;
import java.util.List;

public class AdapterMBK extends RecyclerView.Adapter<AdapterMBK.MyViewHolder> {


    List<RelawanMBK> relawanmbkList;
    private Context context;
    private OnItemClickListener mItemClickListener;
    ProgressBar progressBar;

    public AdapterMBK(Context RelawanMBK, List relawanmbkList) {
        this.relawanmbkList = new ArrayList<>(relawanmbkList);
        this.context = RelawanMBK;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relawanmbk, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nama.setText(relawanmbkList.get(position).getNama());
        holder.nim.setText(relawanmbkList.get(position).getNim());
        holder.fakultas.setText(relawanmbkList.get(position).getFakultas());
    }

    @Override
    public int getItemCount() {
        return relawanmbkList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nim, fakultas;
        ImageView swipe;

        public MyViewHolder(final View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namarelawanmbk);
            nim = itemView.findViewById(R.id.nimrelawanmbk);
            fakultas = itemView.findViewById(R.id.fakultasrelawanmbk);
            swipe = itemView.findViewById(R.id.swipe_right);
            progressBar = itemView.findViewById(R.id.prograss);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailMBK.class);
                    i.putExtra("nim",relawanmbkList.get(getAdapterPosition()).getNim());
                    v.getContext().startActivity(i);
                }

            });
        }

    }
}

