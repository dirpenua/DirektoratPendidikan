package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nama_user")
    private String nama_user;
    @SerializedName("nipnik")
    private String nipnik;
    @SerializedName("password_user")
    private String password;


    public User(String namauser, String nipnik, String password) {
        this.nama_user = namauser;
        this.nipnik = nipnik;
        this.password = password;
    }

    public String getNamaUser() {
        return nama_user;
    }

    public void setNamaUser(String namauser) {
        this.nama_user = namauser;
    }

    public String getNipnik() {
        return nipnik;
    }

    public void setEmail(String nipnik) {
        this.nipnik = nipnik;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
