package com.example.direktoratpendidikan.admin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.direktoratpendidikan.AkreditasiActivity;
import com.example.direktoratpendidikan.BeritaActivity;
import com.example.direktoratpendidikan.DownloadActivity;
import com.example.direktoratpendidikan.FakultasActivity;
import com.example.direktoratpendidikan.KalenderAkademik;
import com.example.direktoratpendidikan.LoginActivity;
import com.example.direktoratpendidikan.NotifikasiActivity;
import com.example.direktoratpendidikan.R;
import com.example.direktoratpendidikan.api.ApiClient;
import com.example.direktoratpendidikan.api.ApiInterface;
import com.example.direktoratpendidikan.data.MSG;
import com.google.android.gms.actions.NoteIntents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaAdminFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public final static String TAG_NAMA = "nama_user";
    public final static String TAG_FOTO = "foto_user";
    public final static String TAG_NIPNIK = "nipnik";
    String nama, foto, nipnik;
    Integer count = 0;
    CarouselView carouselView;
    ImageView lonceng;
    TextView isiToken, notifbadge, increase;
    int[] sampleImages = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    private ApiInterface apiInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda_admin, container, false);

//        isiToken= view.findViewById(R.id.token);
        //replace = view.findViewById(R.id.replacenipnik);
        notifbadge = view.findViewById(R.id.notif_badge);
        lonceng = view.findViewById(R.id.ic_pengumuman);

//        increase = view.findViewById(R.id.increase);
//        increase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateCount(++count);
//            }
//        });
//
//
//        lonceng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateCount(count);
//            }
//        });

        fetchBadge();

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

        sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        nama = getActivity().getIntent().getStringExtra(TAG_NAMA);
//        foto = getActivity().getIntent().getStringExtra(TAG_FOTO);
        nipnik = getActivity().getIntent().getStringExtra(TAG_NIPNIK);

        setDefaultString(TAG_NAMA, nama, getContext());
        setDefaultString(TAG_NIPNIK, nipnik, getContext());

        //replace.setText(nipnik);

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

        final ImageView notifikasi = view.findViewById(R.id.ic_pengumuman);
        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
                //resetBadge();
            }
        });

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

    public static void setDefaultString(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

//    public void updateCount(final int new_hot_number) {
//        count = new_hot_number;
//        if (count <= 0 )
//            notifbadge.setVisibility(View.GONE);
//        else {
//            notifbadge.setVisibility(View.VISIBLE);
//            notifbadge.setText(Integer.toString(count));
//            // supportInvalidateOptionsMenu();
//        }
//    }

    public void fetchBadge (){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MSG> userCall = apiInterface.getBadge(nipnik);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                count = response.body().getBadge();
                if (count <= 0 )
                    notifbadge.setVisibility(View.GONE);
                else {
                    notifbadge.setVisibility(View.VISIBLE);
                    notifbadge.setText(Integer.toString(count));
                    // supportInvalidateOptionsMenu();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                Log.d("onFailure", t.toString());
                String text = "Koneksi sedang tidak stabil. Refresh halaman atau tunggu beberapa saat";
                SpannableString centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                Toast.makeText(getActivity(),centeredText, Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void resetBadge (){
//        ApiInterface service = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<MSG> userCall = service.resetBadge(nipnik);
//        userCall.enqueue(new Callback<MSG>() {
//            @Override
//            public void onResponse(Call<MSG> call, Response<MSG> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MSG> call, Throwable t) {
////                hidepDialog();
//                Log.d("onFailure", t.toString());
//            }
//        });
//    }

}