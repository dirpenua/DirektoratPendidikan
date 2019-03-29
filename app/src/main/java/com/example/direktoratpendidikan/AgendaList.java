package com.example.direktoratpendidikan;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgendaList {
    public String nama_acara;
    public String tempat;
    public Date tanggal;
    public Time jam_mulai;
    public Time jam_akhir;
    public Integer jumlah_undangan;

    public AgendaList() {
    }

    public AgendaList(String nama, String tempat, Date tanggal, Time jam_mulai, Time jam_akhir, Integer jumlah_undangan) {
        this.nama_acara = nama;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.jam_mulai = jam_mulai;
        this.jam_akhir = jam_akhir;
        this.jumlah_undangan = jumlah_undangan;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama_acara", nama_acara);
        result.put("tempat", tempat);
        result.put("tanggal", tanggal);
        result.put("jam_mulai", jam_mulai);
        result.put("jam_akhir", jam_akhir);
        result.put("jumlah_undangan", jumlah_undangan);
        return result;
    }

}

