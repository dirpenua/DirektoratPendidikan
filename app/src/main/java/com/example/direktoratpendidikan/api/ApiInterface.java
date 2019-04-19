package com.example.direktoratpendidikan.api;

import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getlist.php?")
    Call < List<Agenda>> getAgenda(
            @Query("item_type") String item_type,
            @Query("spinner_hari") String spinner_hari
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<MSG> userLogIn(@Field("nipnik") String nipnik,
                        @Field("password_baru") String password_baru
    );


//    @POST("getlist.php?")
//    Call < List<Agenda>> postSpinner(
//            @Query("spinner_hari") String spinner_hari
//    );

    //@FormUrlEncoded
    //@POST("getlist.php")
    //Call<List<Agenda>> getAgenda(@Field("item_type") String item_type);

}
