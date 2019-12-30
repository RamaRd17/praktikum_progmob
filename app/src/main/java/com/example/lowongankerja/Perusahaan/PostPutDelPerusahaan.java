package com.example.lowongankerja.Perusahaan;

import com.google.gson.annotations.SerializedName;

public class PostPutDelPerusahaan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    ResultPerusahaan mPerusahaan;
    @SerializedName("message")
    String message;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public ResultPerusahaan getPerusahaan() {
        return mPerusahaan;
    }
    public void setmPerusahaan(ResultPerusahaan perusahaan) {
        mPerusahaan = perusahaan;
    }
}
