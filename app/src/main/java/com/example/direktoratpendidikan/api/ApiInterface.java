package com.example.direktoratpendidikan.api;

import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.data.Prosedur;

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

    @GET("getbeasiswa.php?")
    Call < List<Beasiswa>> getBeasiswa(
            @Query("kategori") Integer kategori
    );

    @GET("getberita.php?")
    Call < List<Berita>> getBerita(
            @Query("kategori") Integer kategori
    );

    @GET("getprosedur.php?")
    Call < List<Prosedur>> getProsedur(
            @Query("kategori") Integer kategori
    );

    @GET("getprosedur.php?")
    Call < List<Download>> getDw(
            @Query("kategori") Integer kategori
    );


    @FormUrlEncoded
    @POST("searchbeasiswa.php")
    Call<Cari> searchBeasiswa(@Field("search") String search,
                              @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchprosedur.php")
    Call<Cari> searchProsedur(@Field("search") String search,
                              @Field("kategori") String kategori
    );

    @GET("getlistadmin.php?")
    Call < List<Agenda>> getAgendaAdmin(
            @Query("item_type") String item_type,
            @Query("spinner_hari") String spinner_hari
    );

    @GET("getdownload.php?")
    Call < List<Download>> getDownload(
            @Query("item_type") String item_type
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

    @FormUrlEncoded
    @POST("changepassword.php")
    Call<MSG> changePassword(@Field("nipnik") String nipnik,
                             @Field("password_baru") String password_baru,
                             @Field("password_lama") String password_lama
    );

    @FormUrlEncoded
    @POST("tambahdosen.php")
    Call<MSG> tambahDosen (@Field("nipnik") String nipnik,
                           @Field("nama") String nama
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
