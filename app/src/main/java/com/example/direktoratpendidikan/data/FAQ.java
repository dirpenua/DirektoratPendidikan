package com.example.direktoratpendidikan.data;

import com.google.gson.annotations.SerializedName;


public class FAQ {

    @SerializedName("pertanyaan") private String pertanyaan;
    @SerializedName("jawaban") private String jawaban;


    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getJawaban() { return jawaban; }

}
