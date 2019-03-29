package com.example.direktoratpendidikan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class BerandaFragment extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4};
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        carouselView = view.findViewById(R.id.image_carousel);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        return view;
    }

}
