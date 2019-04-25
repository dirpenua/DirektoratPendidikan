package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Beasiswa {
    @SerializedName("id") private String  id;
    @SerializedName("judul") private String judul;
    @SerializedName("kontenpendek") private String kontenpendek;
    @SerializedName("konten") private String kontenpanjang;
    @SerializedName("tgl_publish") private String tgl_publish;


    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getKontenPendek() { return kontenpendek; }
    public String getKontenPanjang() { return kontenpanjang; }
    public String getTanggal() { return tgl_publish; }



}
