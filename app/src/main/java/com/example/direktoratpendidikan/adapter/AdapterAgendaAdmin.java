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
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.DetailAgenda;
import com.example.direktoratpendidikan.DetailAgendaAdmin;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAgendaAdmin extends RecyclerView.Adapter<AdapterAgendaAdmin.MyViewHolder> {


    List<Agenda> agendaList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterAgendaAdmin(Context fragmentAgenda, List agendaList) {
        this.agendaList = new ArrayList<>(agendaList);
        this.context = fragmentAgenda;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda_admin, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tanggal.setText(agendaList.get(position).getTanggal());
        holder.nama_kegiatan.setText(agendaList.get(position).getNama());
        holder.idagenda.setText(agendaList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, idagenda, nama_kegiatan;
        ImageView hapus;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            idagenda = itemView.findViewById(R.id.idagenda);
            nama_kegiatan = itemView.findViewById(R.id.nama_kegiatan);
            hapus = itemView.findViewById(R.id.hapus);
            itemView.setTag(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailAgendaAdmin.class);
                    i.putExtra("idagenda", agendaList.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }

            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String agendaid = agendaList.get(getAdapterPosition()).getId();
                    final String namakegiatan = agendaList.get(getAdapterPosition()).getNama();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(((Activity) context));

                    alertDialogBuilder
                            .setMessage("Anda yakin ingin menghapus agenda: " + namakegiatan + " ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    hapusAgenda(agendaid);
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
            public void hapusAgenda(String agendaid){
                ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
                Call<MSG> userCall = service.hapusAgenda(agendaid);
                final String nama = agendaList.get(getAdapterPosition()).getNama();
                userCall.enqueue(new Callback<MSG>() {
                    @Override
                    public void onResponse(Call<MSG> call, Response<MSG> response) {
                        Log.d("SUKSESNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                        if(response.body().getSuccess() == 1) {
                            String text = nama + " berhasil dihapus";
                            Spannable centeredText = new SpannableString(text);
                            centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                    0, text.length() - 1,
                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                            Toast.makeText(itemView.getContext(),centeredText, Toast.LENGTH_LONG).show();
                        }else {
                            String text = nama + " gagal dihapus";
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


