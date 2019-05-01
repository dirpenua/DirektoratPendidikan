package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Prosedur {
    @SerializedName("id") private String  id;
    @SerializedName("judul") private String judul;
    @SerializedName("kontenpendek") private String kontenpendek;
    @SerializedName("konten") private String kontenpanjang;
    @SerializedName("gambar") private String gambarprosedur;
    @SerializedName("tgl_publish") private String tgl_publish;
    @SerializedName("linkprosedur") private String linkprosedur;


    public String getIdPro() { return id; }
    public String getJudul() { return judul; }
    public String getKontenPendek() { return kontenpendek; }
    public String getKontenPanjang() { return kontenpanjang; }
    public String getGambarPro() { return gambarprosedur; }
    public String getTanggal() { return tgl_publish; }
    public String getLinkPro() { return linkprosedur; }


}
