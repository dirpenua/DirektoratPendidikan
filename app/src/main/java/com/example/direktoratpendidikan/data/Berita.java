package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Berita {
    @SerializedName("tanggal") private String tanggal;
    @SerializedName("bulan") private String bulan;
    @SerializedName("judul") private String judul;
    @SerializedName("konten") private String konten;
    @SerializedName("gambar") private String gambar;
    @SerializedName("tgl_publish") private String tgl_publish;



    public String getJudulB() { return judul; }
    public String getTanggalB() { return tanggal; }
    public String getBulanB() { return bulan; }
    public String getGambarB() { return gambar; }
    public String getKontenB() { return konten; }
    public String getTanggalPublishB() { return tgl_publish; }



}
