package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class Agenda {
    @SerializedName("agenda_id") private String agenda_id;
    @SerializedName("tanggal") private String tanggal;
    @SerializedName("jammulai") private String jammulai;
    @SerializedName("jamselesai") private String jamselesai;
    @SerializedName("bulantahun") private String bulantahun;
    @SerializedName("nama_kegiatan") private String nama_kegiatan;
    @SerializedName("jumlah_undangan") private String jumlah_undangan;
    @SerializedName("tempat") private String tempat;
    @SerializedName("nohp_narahubung") private String nohp_narahubung;
    @SerializedName("notif") private String notif;
    @SerializedName("success") private Integer success;

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

    public String getJammulai() { return jammulai; }

    public void setJammulai(String jammulai) { this.jammulai = jammulai;
    }

    public String getJamselesai() { return jamselesai; }

    public void setJamselesai(String jamselesai) { this.tanggal = jamselesai;
    }

    public String getNama() {
        return nama_kegiatan;
    }

    public void setNama(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public String getTempat() {
        return tempat;
    }

    public String getNarahubung() {
        return nohp_narahubung;
    }

    public String getJumlahUndangan() {
        return jumlah_undangan;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
