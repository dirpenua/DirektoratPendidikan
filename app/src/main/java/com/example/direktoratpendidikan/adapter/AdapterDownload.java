package com.example.direktoratpendidikan.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.DownloadActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.Download;

import java.util.ArrayList;
import java.util.List;

public class AdapterDownload extends RecyclerView.Adapter<AdapterDownload.MyViewHolder> {


    List<Download> downloadList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterDownload(Context Download, List downloadList) {
        this.downloadList = new ArrayList<>(downloadList);
        this.context = Download;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.namafile.setText(downloadList.get(position).getNama());
        holder.linkfile.setText(downloadList.get(position).getLink());



    }

    @Override
    public int getItemCount() {
        return downloadList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namafile, linkfile;

        public MyViewHolder(final View itemView) {
            super(itemView);
            namafile = itemView.findViewById(R.id.namafile);
            linkfile = itemView.findViewById(R.id.linkfile);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int pos = getAdapterPosition();
                        String url = downloadList.get(pos).getLink();
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(i);
                        v.getContext().startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "File tidak ditemukan. Harap hubungi admin.",  Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

            });
        }
    }
}

