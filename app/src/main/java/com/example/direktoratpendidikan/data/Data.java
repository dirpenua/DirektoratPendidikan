package com.example.direktoratpendidikan.data;

import java.sql.Timestamp;

public class Data {
    private String idagenda, namakegiatan, tempat;
    private Timestamp mulai, selesai;
    private  Integer jumlahundangan;

    public Data() {
    }

    public Data(String agenda_id, String nama_kegiatan, Timestamp tgljam_mulai, Timestamp tgljam_selesai, Integer jumlah_undangan, String tempat) {
        this.idagenda = agenda_id;
        this.namakegiatan = nama_kegiatan;
        this.mulai = tgljam_mulai;
        this.selesai = tgljam_selesai;
        this.jumlahundangan = jumlah_undangan;
        this.tempat = tempat;
    }

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
