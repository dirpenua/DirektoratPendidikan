package com.example.direktoratpendidikan.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.direktoratpendidikan.AkreditasiActivity;
import com.example.direktoratpendidikan.BeritaActivity;
import com.example.direktoratpendidikan.DownloadActivity;
import com.example.direktoratpendidikan.FakultasActivity;
import com.example.direktoratpendidikan.KalenderAkademik;
import com.example.direktoratpendidikan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaAdminFragment extends Fragment {

    CarouselView carouselView;
    TextView isiToken;
    int[] sampleImages = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4};
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda_admin, container, false);

//        isiToken= view.findViewById(R.id.token);

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


        carouselView = view.findViewById(R.id.image_carousel);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

//        ImageView user = view.findViewById(R.id.ic_user);
//        user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent user = new Intent(getActivity(), UserActivity.class);
//                startActivity(user);
//            }
//        });

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
                Intent disabilitas = new Intent(getActivity(), DisabilitasAdm.class);
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