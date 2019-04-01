package com.example.direktoratpendidikan;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.direktoratpendidikan.adapter.Adapter;
import com.example.direktoratpendidikan.data.Data;
import com.example.direktoratpendidikan.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.direktoratpendidikan.app.AppController.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mToolbar;
    private Spinner mSpinner;
    SwipeRefreshLayout swipe;
    Adapter adapter;
    private String url = Server.URL +"getlistagenda.php";
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<Data> mItems;
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "username";
    private String mParam1;
    private String mParam2;

    public AgendaFragment() {
        // Required empty public constructor
    }

    public static AgendaFragment newInstance(String param1, String param2) {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_list_agenda);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_agenda);
        mItems = new ArrayList<>();

        adapter = new Adapter(getActivity(), mItems);
        mRecyclerView.setAdapter(adapter);


        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           //        itemList.clear();
                           adapter.notifyDataSetChanged();
                           koneksi();
                       }
                   }
        );

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

    public void onRefresh() {
        mItems.clear();
        adapter.notifyDataSetChanged();
        koneksi();
    }

    private void koneksi(){
       // LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        //mItems.setLayoutManager(llm);
        //mItems.setAdapter( adapter );
        mItems.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,"Register Response: " + response.toString());


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject obj = jsonArray.getJSONObject(i);

                        Data item = new Data();

                        item.setNama(obj.getString("nama_kegiatan"));
                        // menambah item ke array
                        mItems.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi perubahan data adapter
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka ubah
                params.put("0", mParam1);
                return params;

            }};
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


