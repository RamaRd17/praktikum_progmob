package com.example.lowongankerja.Perusahaan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPerusahaan {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nama_perusahaan")
    @Expose
    private String nama_perusahaan;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("tentang")
    @Expose
    private String tentang;



    public  String getAlamat(){
        return lokasi;
    }

    public void SetAlamat(String lokasi){
        this.lokasi = lokasi;
    }

    public String getPerusahaan() {
        return nama_perusahaan;
    }

    public void setPerusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTentang() {
        return tentang;
    }

    public void setTentang(String tentang) {
        this.tentang = tentang;
    }
}
