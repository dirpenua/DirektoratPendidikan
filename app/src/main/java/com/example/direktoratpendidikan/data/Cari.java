package com.example.direktoratpendidikan.data;

import java.util.List;

public class Cari {
    String value;
    String message;
    List<Beasiswa> result;
    List<Prosedur> resultpro;
    List<Download> resultdw;

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
}
