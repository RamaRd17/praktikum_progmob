package com.example.lowongankerja.User;

import java.util.List;

public class ValueUser {
    String value;
    String message;
    String email;
    String name;
    String image;
    List<ResultProfile> result;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public List<ResultProfile> getResult() {
        return result;
    }


    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }
}
