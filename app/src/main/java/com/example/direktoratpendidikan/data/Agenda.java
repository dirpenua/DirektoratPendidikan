package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Agenda {
    @SerializedName("idagenda") private String idagenda;
    @SerializedName("namakegiatan") private String namakegiatan;
    @SerializedName("tempat") private String tempat;
    @SerializedName("mulai") private Timestamp mulai;
    @SerializedName("selesai") private Timestamp selesai;
    @SerializedName("jumlahundangan") private  Integer jumlahundangan;


    public String getId() {
        return idagenda;
    }

    public void setId(String id_agenda) {
        this.idagenda = id_agenda;
    }

    public String getNama() {
        return namakegiatan;
    }

    public void setNama(String nama_kegiatan) {
        this.namakegiatan = nama_kegiatan;
    }

}
