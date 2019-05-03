package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Akreditasi {
    @SerializedName("jumlah") private String jumlah;
    @SerializedName("judul") private String judul;
    @SerializedName("link") private String link;

    public String getJumlahAk() { return jumlah; }
    public String getJudulAk() { return judul; }
    public String getLinkAk() { return link; }



}
