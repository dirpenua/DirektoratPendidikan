package com.example.direktoratpendidikan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.admin.DisabilitasAdm;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.mahasiswa.DisabilitasMhs;
import com.example.direktoratpendidikan.mahasiswa.SplashDisabilitas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.OnClick;



public class BerandaFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    String nama;

    CarouselView carouselView;
    TextView isiToken, namaLengkap;


    int[] sampleImages = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

//        isiToken= view.findViewById(R.id.token);
//        namaLengkap = view.findViewById(R.id.namalengkap);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
//                            isiToken.setText("Token : " + token);
                        } else {

                        }
                    }
                });

        sharedpreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getActivity().getIntent().getStringExtra(TAG_NAMA);
//        namaLengkap.setText(nama);

        carouselView = view.findViewById(R.id.image_carousel);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);


        ImageView berita = view.findViewById(R.id.ic_berita);
        berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent berita = new Intent(getActivity(), BeritaActivity.class);
                startActivity(berita);
            }
        });

        ImageView kalender = view.findViewById(R.id.ic_kalender);
        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kalender = new Intent(getActivity(), KalenderAkademik.class);
                startActivity(kalender);
            }
        });

        ImageView prodi = view.findViewById(R.id.ic_prodi);
        prodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prodi = new Intent(getActivity(), FakultasActivity.class);
                startActivity(prodi);
            }
        });

        ImageView akreditasi = view.findViewById(R.id.ic_akreditasi);
        akreditasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent akreditasi = new Intent(getActivity(), AkreditasiActivity.class);
                startActivity(akreditasi);
            }
        });

        ImageView disabilitas = view.findViewById(R.id.ic_disabilitas);
        disabilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disabilitas = new Intent(getActivity(), SplashDisabilitas.class);
                startActivity(disabilitas);
            }
        });

        ImageView download = view.findViewById(R.id.ic_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent download = new Intent(getActivity(), DownloadActivity.class);
                startActivity(download);
            }
        });

        return view;
    }

}
