package com.example.direktoratpendidikan.api;

import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Notif;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getlist.php?")
    Call < List<Agenda>> getProsedur(
            @Query("item_type") String item_type,
            @Query("spinner_hari") String spinner_hari,
            @Query("nipnik") String nipnik
    );

    @GET("getlist.php?")
    Call < List<Agenda>> getAgenda(
            @Query("item_type") String item_type,
            @Query("spinner_hari") String spinner_hari,
            @Query("nipnik") String nipnik
    );

    @GET("getlistadmin.php?")
    Call < List<Agenda>> getAgendaAdmin(
            @Query("item_type") String item_type,
            @Query("spinner_hari") String spinner_hari
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<MSG> userLogIn(@Field("nipnik") String nipnik,
                        @Field("password_user") String password_user
    );

    @FormUrlEncoded
    @POST("ubahpassword.php")
    Call<MSG> resetPassword(@Field("nipnik") String nipnik,
                            @Field("password_reset") String password_reset
    );

//    @FormUrlEncoded
//    @POST("regpushnotif.php")
//    Observable<Notif> registerToken (@Field("token") String token
//    );

    @FormUrlEncoded
    @POST("savetoken.php")
    Call<Notif> saveToken (@Field("nipnik") String nipnik,
                                 @Field("token") String token
    );


//    @POST("getlist.php?")
//    Call < List<Agenda>> postSpinner(
//            @Query("spinner_hari") String spinner_hari
//    );

    //@FormUrlEncoded
    //@POST("getlist.php")
    //Call<List<Agenda>> getAgenda(@Field("item_type") String item_type);

}
