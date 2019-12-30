package com.example.lowongankerja.StafDivisi;

import java.util.List;

public class GetStafDivisi {
    String status;
    List<ResultStafDivisi> result = null;
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

    public List<ResultStafDivisi> getResult() {
        return result;
    }
}
