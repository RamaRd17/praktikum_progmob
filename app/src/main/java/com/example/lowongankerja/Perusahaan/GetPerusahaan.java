package com.example.lowongankerja.Perusahaan;

import java.util.List;

public class GetPerusahaan {
    String status;
    List<ResultPerusahaan> result = null;
    List<ResultDetail> test = null;
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
    public List<ResultPerusahaan> getResult() {
        return result;
    }
}
