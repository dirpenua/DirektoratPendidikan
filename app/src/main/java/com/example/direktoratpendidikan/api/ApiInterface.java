package com.example.direktoratpendidikan.api;

import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Akreditasi;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Dosen;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.Fakultas;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.data.Profil;
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

    @GET("getakreditasi.php?")
    Call < List<Akreditasi>> getAkreditasi(
            @Query("kategori") Integer kategori
    );

    @FormUrlEncoded
    @POST("getpeserta.php?")
    Call < List<Dosen>> getListPeserta(
            @Field("kategori") String kategori,
            @Field("agendaid") String agendaid
    );

    @FormUrlEncoded
    @POST("getnotifikasi.php?")
    Call < List<MSG>> getNotifikasi(
            @Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("getpesertaada.php?")
    Call < List<Dosen>> getPesertaAda(
            @Field("kategori") String kategori,
            @Field("agendaid") String agendaid
    );

    @FormUrlEncoded
    @POST("tambahpeserta.php")
    Call<MSG> tambahPeserta (@Field("agendaid") String agendaid,
                             @Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("hapuspeserta.php")
    Call<MSG> hapusPeserta (@Field("agendaid") String agendaid,
                             @Field("nipnik") String nipnik
    );

    @GET("getfakultas.php?")
    Call < List<Fakultas>> getFakultas(
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

    @GET("getkalender.php?")
    Call < List<Download>> getKalender(
            @Query("kategori") String id
    );

    @GET("getprodi.php?")
    Call < List<Fakultas>> getProdi(
            @Query("idfakultas") String idfakultas
    );

    @FormUrlEncoded
    @POST("displayakun.php")
    Call<Profil> userPic (@Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("searchbeasiswa.php")
    Call<Cari> searchBeasiswa(@Field("search") String search,
                              @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchtambahpeserta.php")
    Call<Cari> searchTambahDsn(@Field("search") String search,
                               @Field("idagenda") String idagenda,
                               @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchpesertaada.php")
    Call<Cari> searchPesertaAda(@Field("search") String search,
                               @Field("idagenda") String idagenda,
                               @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchberita.php")
    Call<Cari> searchBerita(@Field("search") String search
    );

    @FormUrlEncoded
    @POST("searchprosedur.php")
    Call<Cari> searchProsedur(@Field("search") String search,
                              @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchfakultas.php")
    Call<Cari> searchFakultas(@Field("search") String search
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
    @POST("detailagenda.php")
    Call<Agenda> getDetailAgenda(  @Field("idagenda") String idagenda
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


    @FormUrlEncoded
    @POST("tambahagenda.php")
    Call<MSG> tambahAgenda (@Field("namakegiatan") String namakegiatan,
                            @Field("tempat") String tempat,
                            @Field("narahubung") String narahubung,
                            @Field("tgljammulai") String tgljammulai,
                            @Field("tgljamselesai") String tgljamselesai
    );

    @FormUrlEncoded
    @POST("ubahagenda.php")
    Call<MSG> ubahAgenda   (@Field("agendaid") String agendaid,
                            @Field("namakegiatan") String namakegiatan,
                            @Field("tempat") String tempat,
                            @Field("narahubung") String narahubung,
                            @Field("tgljammulai") String tgljammulai,
                            @Field("tgljamselesai") String tgljamselesai
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
