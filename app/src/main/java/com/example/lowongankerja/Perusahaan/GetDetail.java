package com.example.lowongankerja.Perusahaan;

import java.util.List;

public class GetDetail {
    String status;
    List<ResultDetail> result = null;
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
    public List<ResultDetail> getResult() {
        return result;
    }
}
