package com.example.direktoratpendidikan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.direktoratpendidikan.R;

import java.util.List;


public class SpinnerCariHitamAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mValuesList;

    public SpinnerCariHitamAdapter(Context mContext, List<String> mValuesList) {
        this.mContext = mContext;
        this.mValuesList = mValuesList;
    }

    @Override
    public int getCount() {
        return mValuesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mValuesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.custom_spinner_dropdown_cari, parent, false);
            view.setTag("DROPDOWN");
        }

        TextView textView = view.findViewById(R.id.spinner_item_cari);
        textView.setText(getTitle(position));

        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.custom_spinner_toolbar_carihitam, parent, false);
            view.setTag("NON_DROPDOWN");
        }

        TextView textView = view.findViewById(R.id.spinner_item_cari);
        textView.setText(getTitle(position));
        return view;
    }

    private String getTitle(int position) {
        return position >= 0 && position < mValuesList.size() ? mValuesList.get(position) : "";
    }
}