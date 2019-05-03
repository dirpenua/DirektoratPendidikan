package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class Profil {
    @SerializedName("nama") private String namaprofil;
    @SerializedName("foto") private String foto;

    public String getFotoProfil() { return foto; }
    public String getNamaProfil() { return namaprofil; }



}
