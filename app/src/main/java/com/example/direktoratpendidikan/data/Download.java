package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class Download {
    @SerializedName("namafile") private String namafile;
    @SerializedName("linkfile") private String linkfile;

    public String getNama() { return namafile; }

    public void setNama(String nama) { this.namafile = nama;
    }

    public String getLink() { return linkfile; }

    public void setLink(String link) { this.linkfile = link;
    }

}
