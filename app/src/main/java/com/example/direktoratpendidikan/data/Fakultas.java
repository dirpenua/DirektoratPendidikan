package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Fakultas {
    @SerializedName("nama") private String nama;
    @SerializedName("gambar") private String gambar;
    @SerializedName("gmaps") private String gmaps;
    @SerializedName("notelp") private String notelp;
    @SerializedName("email") private String email;
    @SerializedName("kampus") private String kampus;
    @SerializedName("alamat") private String alamat;
    @SerializedName("fax") private String fax;
    @SerializedName("id") private String id;
    @SerializedName("namaprodi") private String namaprodi;

    public String getIdF() { return id; }
    public String getNamaF() { return nama; }
    public String getGambarF() { return gambar; }
    public String getGmapsF() { return gmaps; }
    public String getTelpF() { return notelp; }
    public String getEmailF() { return email; }
    public String getKampusF() { return kampus; }
    public String getAlamatF() { return alamat; }
    public String getFaxF() { return fax; }
    public String getNamaProdi() { return namaprodi; }



}
