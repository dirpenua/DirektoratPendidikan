package com.example.direktoratpendidikan;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment {

    private Toolbar mToolbar;
    private Spinner mSpinner;

    public AgendaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSpinner = view.findViewById(R.id.spinner_rss);

        String[] items = getResources().getStringArray(R.array.hari);
        List<String> spinnerItems = new ArrayList<String>();

        for (int i = 0; i < items.length; i++) {
            spinnerItems.add(items[i]);
        }

        SpinnerAdapter adapter = new SpinnerAdapter(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext(), spinnerItems);
        mSpinner.setAdapter(adapter);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSpinner.setDropDownVerticalOffset(-116);
        }

        return view;

    }

}
