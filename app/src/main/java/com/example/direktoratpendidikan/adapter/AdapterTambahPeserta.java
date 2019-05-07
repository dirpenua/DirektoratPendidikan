package com.example.direktoratpendidikan.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.direktoratpendidikan.admin.TabTambahPeserta;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.Dosen;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.MSG;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTambahPeserta extends RecyclerView.Adapter<AdapterTambahPeserta.MyViewHolder> {


    List<Dosen> pesertaList;
    private Context context;
    private OnItemClickListener mItemClickListener;
    ProgressBar progressBar;

    public AdapterTambahPeserta(Context Dosen, List pesertaList) {
        this.pesertaList = new ArrayList<>(pesertaList);
        this.context = Dosen;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tambahpeserta, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nama.setText(pesertaList.get(position).getNama());
        holder.nipnik.setText(pesertaList.get(position).getNipnik());
    }

    @Override
    public int getItemCount() {
        return pesertaList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nipnik;
        ImageView tambah;

        public MyViewHolder(final View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namapeserta);
            nipnik = itemView.findViewById(R.id.nipnikpeserta);
            tambah = itemView.findViewById(R.id.tambah);
            progressBar = itemView.findViewById(R.id.prograss);
            itemView.setTag(itemView);
            Intent intent = ((Activity) context).getIntent(); //Untuk mendapatkan putExtra yang dari activity
            final String idagenda = intent.getStringExtra("idagenda");
            tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String agendaid = idagenda;
                    String nipnik = pesertaList.get(getAdapterPosition()).getNipnik();
                    tambahPeserta(agendaid, nipnik);
                }
            });
        }

        public void tambahPeserta(String agendaid, String nipnik){
            ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
            Call<MSG> userCall = service.tambahPeserta(agendaid, nipnik);
            userCall.enqueue(new Callback<MSG>() {
                @Override
                public void onResponse(Call<MSG> call, Response<MSG> response) {
                    Log.d("SUKSERNYA", "SUKSESNYA APA: " + response.body().getSuccess());
                    if(response.body().getSuccess() == 1) {
                        String text = response.body().getMessage();
                        Spannable centeredText = new SpannableString(text);
                        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                0, text.length() - 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        Toast.makeText(itemView.getContext(),centeredText, Toast.LENGTH_LONG).show();
                    }else {
                        String text = "" + response.body().getMessage();
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

