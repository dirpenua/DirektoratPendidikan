package com.example.direktoratpendidikan.data;

import java.util.List;

public class Cari {
    String value;
    String message;
    List<Beasiswa> result;
    List<Prosedur> resultpro;
    List<Download> resultdw;
    List<Berita> resultbr;
    List<Fakultas> resultfk;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Beasiswa> getListBeasiswa() {
        return result;
    }

    public List<Prosedur> getListProsedur() {
        return resultpro;
    }

    public List<Download> getListDownload() {
        return resultdw;
    }

    public List<Berita> getListBerita() {
        return resultbr;
    }

    public List<Fakultas> getListFakultas() {
        return resultfk;
    }
}
