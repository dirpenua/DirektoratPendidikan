package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class RelawanMBK {
    @SerializedName("nim") private String nim;
    @SerializedName("nama") private String nama;
    @SerializedName("namapanggilan") private String namapanggilan;
    @SerializedName("fakultas") private String fakultas;
    @SerializedName("prodi") private String prodi;
    @SerializedName("alamatrumah") private String alamatrumah;
    @SerializedName("alamatkos") private String alamatkos;
    @SerializedName("asalkota") private String asalkota;
    @SerializedName("asalprovinsi") private String asalprovinsi;
    @SerializedName("nohp") private String nohp;
    @SerializedName("semester") private String semester;
    @SerializedName("ipk") private String ipk;
    @SerializedName("prestasi") private String prestasi;
    @SerializedName("disabilitas") private String disabilitas;
    @SerializedName("email") private String email;


    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama;
    }

    public String getNamapanggilan() { return namapanggilan; }

    public String getAlamatrumah() { return alamatrumah; }

    public String getAlamatkos() { return alamatkos; }

    public String getNim() { return nim; }

    public void setNim(String nim) { this.nim= nim;
    }

    public String getFakultas() { return fakultas; }

    public void setFakultas(String fakultas) { this.fakultas= fakultas;
    }

    public String getAsalkota() { return asalkota; }
    public String getAsalprovinsi() { return asalprovinsi; }
    public String getProdi() { return prodi; }
    public String getNohp() { return nohp; }
    public String getSemester() { return semester; }
    public String getIpk() { return ipk; }
    public String getPrestasi() { return prestasi; }
    public String getDisabilitas() { return disabilitas; }
    public String getEmail() { return email; }

}
