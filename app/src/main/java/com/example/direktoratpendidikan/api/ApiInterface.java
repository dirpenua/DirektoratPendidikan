package com.example.direktoratpendidikan.api;

import com.example.direktoratpendidikan.data.Agenda;
import com.example.direktoratpendidikan.data.Akreditasi;
import com.example.direktoratpendidikan.data.Beasiswa;
import com.example.direktoratpendidikan.data.Berita;
import com.example.direktoratpendidikan.data.Cari;
import com.example.direktoratpendidikan.data.Dosen;
import com.example.direktoratpendidikan.data.Download;
import com.example.direktoratpendidikan.data.FAQ;
import com.example.direktoratpendidikan.data.Fakultas;
import com.example.direktoratpendidikan.data.MSG;
import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.data.Profil;
import com.example.direktoratpendidikan.data.Prosedur;
import com.example.direktoratpendidikan.data.RelawanMBK;

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
    @POST("getmbk.php?")
    Call < List<RelawanMBK>> getListMBK(
            @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("getrelawan.php?")
    Call < List<RelawanMBK>> getListRelawan(
            @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("getpermintaanrelawan.php?")
    Call < List<RelawanMBK>> getPermintaanRelawan(
            @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("getnotifikasi.php?")
    Call < List<MSG>> getNotifikasi(
            @Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("getnotifikasiharian.php?")
    Call <MSG> getNotifikasiHarian(
            @Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("getbadge.php?")
    Call <MSG> getBadge(
            @Field("nipnik") String nipnik
    );

    @FormUrlEncoded
    @POST("resetbadge.php?")
    Call <MSG> resetBadge(
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

    @FormUrlEncoded
    @POST("hapusagenda.php")
    Call<MSG> hapusAgenda (@Field("agendaid") String agendaid
    );

    @FormUrlEncoded
    @POST("hapusmbk.php")
    Call<MSG> hapusMBK (@Field("nim") String nim
    );

    @FormUrlEncoded
    @POST("hapusrelawan.php")
    Call<MSG> hapusRelawan (@Field("nim") String nim
    );

    @FormUrlEncoded
    @POST("tambahrelawan.php")
    Call<MSG> tambahRelawan (@Field("nim") String nim
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

    @GET("getfaq.php?")
    Call < List<FAQ>> getFAQ(
            @Query("kategori") String kategori
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
    @POST("searchfaq.php")
    Call<Cari> searchFaq(@Field("search") String search
    );

    @FormUrlEncoded
    @POST("searchtambahpeserta.php")
    Call<Cari> searchTambahDsn(@Field("search") String search,
                               @Field("idagenda") String idagenda,
                               @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchmbk.php")
    Call<Cari> searchMBK(@Field("search") String search,
                         @Field("kategori") String kategori
    );

    @FormUrlEncoded
    @POST("searchrelawan.php")
    Call<Cari> searchRelawan (@Field("search") String search,
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
    @POST("detailrelawan.php")
    Call<RelawanMBK> getDetailRelawan(  @Field("nim") String nim
    );

    @FormUrlEncoded
    @POST("detailmbk.php")
    Call<RelawanMBK> getDetailMBK(  @Field("nim") String nim
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
    @POST("daftarrelawan.php")
    Call<MSG> daftarRelawan (@Field("nim") String nim,
                             @Field("namalengkap") String namalengkap,
                             @Field("namapanggil") String namapanggil,
                             @Field("alamatrumah") String alamatrumah,
                             @Field("alamatkosan") String alamatkosan,
                             @Field("asalkota") String asalkota,
                             @Field("asalprovinsi") String asalprovinsi,
                             @Field("nohp") String nohp,
                             @Field("fakultas") String fakultas,
                             @Field("prodi") String prodi,
                             @Field("semester") String semester,
                             @Field("ipk") String ipk,
                             @Field("prestasi") String prestasi
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
