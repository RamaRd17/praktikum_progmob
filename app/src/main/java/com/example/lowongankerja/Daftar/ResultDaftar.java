package com.example.lowongankerja.Daftar;

public class ResultDaftar {
    String nama_perusahaan;

    public int getId_daftar() {
        return id_daftar;
    }

    public void setId_daftar(int id_daftar) {
        this.id_daftar = id_daftar;
    }

    int id_daftar;
    int biaya;
    int fee;
    String nama_user;

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }


    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_stafdivisi() {
        return nama_stafdivisi;
    }

    public void setNama_stafdivisi(String nama_stafdivisi) {
        this.nama_stafdivisi = nama_stafdivisi;
    }

    int id_user;
    String nama_stafdivisi;
}
