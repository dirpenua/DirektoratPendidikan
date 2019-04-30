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

import java.util.ArrayList;
import java.util.List;

public class AdapterDownloadPros extends RecyclerView.Adapter<AdapterDownloadPros.MyViewHolder> {


    List<Download> downloadList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterDownloadPros(Context Download, List downloadList) {
        this.downloadList = new ArrayList<>(downloadList);
        this.context = Download;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downloadpros, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.namafile.setText(downloadList.get(position).getNama());
        holder.linkfile.setText(downloadList.get(position).getLink());
        holder.kontenpro.setText(downloadList.get(position).getKonten());
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
        TextView namafile, linkfile, kontenpro;

        public MyViewHolder(final View itemView) {
            super(itemView);
            namafile = itemView.findViewById(R.id.namafile);
            linkfile = itemView.findViewById(R.id.linkfile);
            kontenpro = itemView.findViewById(R.id.kontenpro);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String url = downloadList.get(pos).getLink();
                    try {
                        if(url!= null) {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            v.getContext().startActivity(i);
                        } else{
                            String text = "File tidak ditemukan. Harap hubungi admin melalui menu BANTUAN";
                            Spannable centeredText = new SpannableString(text);
                            centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                    0, text.length() - 1,
                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                            Toast.makeText(v.getContext(),centeredText, Toast.LENGTH_LONG).show();
                        }
                    } catch (ActivityNotFoundException e) {
                        String text = "File tidak ditemukan. Harap hubungi admin melalui menu BANTUAN";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(v.getContext(), centeredText, Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

            });
        }
    }
}

