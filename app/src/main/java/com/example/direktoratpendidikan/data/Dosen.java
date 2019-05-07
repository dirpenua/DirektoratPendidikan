package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class Dosen {
    @SerializedName("nipnik") private String nipnik;
    @SerializedName("namadosen") private String namadosen;

    public String getNama() { return namadosen; }

    public void setNama(String nama) { this.namadosen = nama;
    }

    public String getNipnik() { return nipnik; }

    public void setNipnik(String nipnik) { this.nipnik = nipnik;
    }

}
