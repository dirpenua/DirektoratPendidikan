package com.example.direktoratpendidikan.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.Fakultas;

import java.util.ArrayList;
import java.util.List;

public class AdapterProdi extends RecyclerView.Adapter<AdapterProdi.MyViewHolder> {


    List<Fakultas> prodiList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterProdi(Context Prodi, List prodiList) {
        this.prodiList = new ArrayList<>(prodiList);
        this.context = Prodi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodi, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.namaprodi.setText(prodiList.get(position).getNamaProdi());



    }

    @Override
    public int getItemCount() {
        return prodiList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namaprodi;
        public MyViewHolder(final View itemView) {
            super(itemView);
            namaprodi = itemView.findViewById(R.id.namaprodi);
            itemView.setTag(itemView);
        }
    }
}

