package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class RelawanMBK {
    @SerializedName("nim") private String nim;
    @SerializedName("nama") private String nama;
    @SerializedName("fakultas") private String fakultas;

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama;
    }

    public String getNim() { return nim; }

    public void setNim(String nim) { this.nim= nim;
    }

    public String getFakultas() { return fakultas; }

    public void setFakultas(String fakultas) { this.fakultas= fakultas;
    }


}
