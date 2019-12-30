package com.example.lowongankerja.Perusahaan;

public class ResultDetail {
    int id_perusahaan;
    int id_divisi;
    int id;
    String nama_perusahaan;
    String lokasi;
    String nama_divisi;
    String nama_stafdivisi;
    String tentang_stafdivisi;
    int biaya;

    public int getId_stafdivisi() {
        return id;
    }

    public void setId_stafdivisi(int id_stafdivisi) {
        this.id = id_stafdivisi;
    }

    public int getId_perusahaan() {
        return id_perusahaan;
    }

    public void setId_perusahaan(int id_perusahaan) {
        this.id_perusahaan = id_perusahaan;
    }

    public int getId_divisi() {
        return id_divisi;
    }

    public void setId_divisi(int id_divisi) {
        this.id_divisi = id_divisi;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNama_divisi() {
        return nama_divisi;
    }

    public void setNama_divisi(String nama_divisi) {
        this.nama_divisi = nama_divisi;
    }

    public String getNama_stafdivisi() {
        return nama_stafdivisi;
    }

    public void setNama_stafdivisi(String nama_stafdivisi) {
        this.nama_stafdivisi = nama_stafdivisi;
    }

    public String getTentang_stafdivisi() {
        return tentang_stafdivisi;
    }

    public void setTentang_stafdivisi(String tentang_stafdivisi) {
        this.tentang_stafdivisi = tentang_stafdivisi;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }
}
