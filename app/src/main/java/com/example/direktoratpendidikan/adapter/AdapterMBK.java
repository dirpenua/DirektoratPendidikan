package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.direktoratpendidikan.admin.DetailMBK;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.RelawanMBK;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.nimprodi.setText(relawanmbkList.get(position).getNimProdi());
        holder.nimhide.setText(relawanmbkList.get(position).getNim());
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
        TextView nama, nimprodi, fakultas, nimhide;
        ImageView hapus;

        public MyViewHolder(final View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namarelawanmbk);
            nimprodi = itemView.findViewById(R.id.nimrelawanmbk);
            nimhide = itemView.findViewById(R.id.hidenimmbk);
            fakultas = itemView.findViewById(R.id.fakultasrelawanmbk);
            hapus = itemView.findViewById(R.id.hapus);
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
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nim = relawanmbkList.get(getAdapterPosition()).getNim();
                    final String nama = relawanmbkList.get(getAdapterPosition()).getNama();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(((Activity) context));

                    alertDialogBuilder
                            .setMessage("Anda yakin " + nama + " sudah tidak aktif lagi ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deactiveMBK(nim);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        }

        public void deactiveMBK(String nim){
            ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
            Call<MSG> userCall = service.hapusMBK(nim);
            final String nama = relawanmbkList.get(getAdapterPosition()).getNama();
            userCall.enqueue(new Callback<MSG>() {
                @Override
                public void onResponse(Call<MSG> call, Response<MSG> response) {
                    Log.d("SUKSESNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                    if(response.body().getSuccess() == 1) {
                        String text = nama + " berhasil dinonaktifkan";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(itemView.getContext(),centeredText, Toast.LENGTH_LONG).show();
                    }else {
                        String text = nama + " gagal dinonaktifkan";
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(itemView.getContext(),centeredText, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MSG> call, Throwable t) {
//                hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }

    }
}

