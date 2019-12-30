package com.example.lowongankerja.StafDivisi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultStafDivisi {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nama_stafdivisi")
    @Expose
    private String nama_stafdivisi;
    @SerializedName("tentang")
    @Expose
    private String tentang ;
    @SerializedName("id_divisi")
    @Expose
    private int id_divisi ;
    @SerializedName("id_perusahaan")
    @Expose
    private int id_perusahaan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_stafdivisi() {
        return nama_stafdivisi;
    }

    public void setNama_stafdivisi(String nama_stafdivisi) {
        this.nama_stafdivisi = nama_stafdivisi;
    }

    public String getTentang() {
        return tentang;
    }

    public void setTentang(String tentang) {
        this.tentang = tentang;
    }

    public int getId_divisi() { return id_divisi ;}

    public void setId_divisi(int id_divisi) {
        this.id_divisi = id_divisi;
    }

    public int getId_perusahaan() {
        return id_perusahaan;
    }

    public void setId_perusahaan(int id_perusahaan) {
        this.id_perusahaan = id_perusahaan;
    }
}
