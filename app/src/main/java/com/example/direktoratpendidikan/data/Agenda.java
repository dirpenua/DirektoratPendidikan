package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class Agenda {
    @SerializedName("agenda_id") private String agenda_id;
    @SerializedName("tanggal") private String tanggal;
    @SerializedName("bulantahun") private String bulantahun;
    @SerializedName("nama_kegiatan") private String nama_kegiatan;

    public String getId() {
        return agenda_id;
    }

    public void setId(String agenda_id) {
        this.agenda_id = agenda_id;
    }

    public String getTanggal() { return tanggal; }

    public void setTanggal(String tanggal) { this.tanggal = tanggal;
    }

   public String getBulantahun() { return bulantahun; }

   public void setBulantahun(String bulantahun) { this.bulantahun = bulantahun;}

    public String getNama() {
        return nama_kegiatan;
    }

    public void setNama(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

}
