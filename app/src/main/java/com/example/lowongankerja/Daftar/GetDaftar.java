package com.example.lowongankerja.Daftar;

import java.util.List;

public class GetDaftar {
    String value;
    String message;
    String email;
    String name;
    List<ResultDaftar> result;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResultDaftar> getResult() {
        return result;
    }

    public void setResult(List<ResultDaftar> result) {
        this.result = result;
    }
}
