package com.example.direktoratpendidikan.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

//    public static final String BASE_URL = "https://bbeasiswa.unair.ac.id/dirpenapp/";


    public static final String BASE_URL = "https://dirpenunair.000webhostapp.com/";
    public static final String IMAGE_URL = "https://dirpenunair.000webhostapp.com/image/";
    public static final String FAKUL_IMAGE_URL = "https://dirpenunair.000webhostapp.com/image/fakultas/";
    public static final String CAROUSEL_IMAGE_URL = "https://dirpenunair.000webhostapp.com/image/carouselview/";

    public static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if (retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}