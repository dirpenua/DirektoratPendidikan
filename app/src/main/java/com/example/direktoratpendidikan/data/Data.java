package com.example.direktoratpendidikan.data;

public class Data {
    private String id, nama, alamat, jk;

    public Data() {
    }

    public Data(String id, String nama, String alamat, String jk) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.jk = jk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getJk() { return jk; }

    public void setJk(String jk) { this.jk = jk; }
}
