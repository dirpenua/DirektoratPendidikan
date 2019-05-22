package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Dosen;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.RelawanMBK;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRelawanMBK extends RecyclerView.Adapter<AdapterRelawanMBK.MyViewHolder> {


    List<RelawanMBK> relawanmbkList;
    private Context context;
    private OnItemClickListener mItemClickListener;
    ProgressBar progressBar;

    public AdapterRelawanMBK(Context RelawanMBK, List relawanmbkList) {
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
            swipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nim = relawanmbkList.get(getAdapterPosition()).getNim();

                }
            });
        }

    }
}

