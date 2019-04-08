package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Agenda {
    @SerializedName("id_agenda") private String id_agenda;
    @SerializedName("nama_kegiatan") private String nama_kegiatan;

    public String getId() {
        return id_agenda;
    }

    public void setId(String id_agenda) {
        this.id_agenda = id_agenda;
    }

    public String getNama() {
        return nama_kegiatan;
    }

    public void setNama(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

}
