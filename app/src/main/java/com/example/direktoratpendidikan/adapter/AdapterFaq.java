package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.data.FAQ;

import java.util.ArrayList;
import java.util.List;

public class AdapterFaq extends RecyclerView.Adapter<AdapterFaq.MyViewHolder> {


    List<FAQ> faqList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    public AdapterFaq(Context fragmentFaq, List faqList) {
        this.faqList = new ArrayList<>(faqList);
        this.context = fragmentFaq;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.pertanyaan.setText(faqList.get(position).getPertanyaan());
        holder.jawaban.setText(faqList.get(position).getJawaban());
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  pertanyaan, jawaban;
        ImageView expand, collapse;

        public MyViewHolder(final View itemView) {
            super(itemView);
            pertanyaan = itemView.findViewById(R.id.pertanyaan);
            jawaban = itemView.findViewById(R.id.jawaban);
            expand = itemView.findViewById(R.id.expand);
            collapse = itemView.findViewById(R.id.collapse);
            itemView.setTag(itemView);

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expand.setVisibility(View.GONE);
                    collapse.setVisibility(View.VISIBLE);
                    jawaban.setVisibility(View.VISIBLE);

                }
            });

            collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collapse.setVisibility(View.GONE);
                    expand.setVisibility(View.VISIBLE);
                    jawaban.setVisibility(View.GONE);
                }
            });


        }

        }
    }


