package com.example.lowongankerja.Divisi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultDivisi {
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_divisi() {
        return nama_divisi;
    }

    public void setNama_divisi(String nama_divisi) {
        this.nama_divisi = nama_divisi;
    }

    @SerializedName("nama_divisi")
    @Expose
    private String nama_divisi;
}
