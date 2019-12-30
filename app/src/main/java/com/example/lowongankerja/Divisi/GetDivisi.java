package com.example.lowongankerja.Divisi;

import com.example.lowongankerja.StafDivisi.ResultStafDivisi;

import java.util.List;

public class GetDivisi {
    String status;
    List<ResultDivisi> result = null;
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

    public List<ResultDivisi> getResult() {
        return result;
    }
}
