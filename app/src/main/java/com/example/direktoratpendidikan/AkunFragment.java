package com.example.direktoratpendidikan;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {

//    SessionManager session;
    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, nipnik;
    TextView _namauser,_nipnik;
    Button btnLogout;

    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);
        //session = new SessionManager(getContext());

        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getActivity().getIntent().getStringExtra(TAG_NAMA);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);


        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
//        if(!session.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(getContext(), LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            getContext().startActivity(i);
//        }else{
//            Intent i = new Intent(getContext(), MainActivity.class);
//            getContext().startActivity(i);
//        }

        // get user data from session
//        HashMap<String, String> user = session.getUserDetails();
//
//        // name
//        String nama = user.get(SessionManager.KEY_NAMA);
//
//        // email
//        String nipnik = user.get(SessionManager.KEY_NIPNIK);

        _namauser = view.findViewById(R.id.akun_namauser);
        _nipnik = view.findViewById(R.id.akun_nipnik);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);

        _namauser.setText(nama);
        _nipnik.setText(nipnik);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and

                //sharedpreferences.logoutSession();

                // After logout redirect user to Login Activity
//                Intent i = new Intent(getContext(), LoginActivity.class);
//                // Closing all the Activities
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                // Add new Flag to start new Activity
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                // Staring Login Activity
//                getContext().startActivity(i);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_NAMA, null);
                editor.putString(TAG_NIPNIK, null);
                editor.commit();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
