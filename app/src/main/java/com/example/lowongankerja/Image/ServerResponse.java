package com.example.lowongankerja.Image;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    boolean success;
    @SerializedName("message")
    String message;

    String getMessage() {
        return message;
    }

    boolean getSuccess() {
        return success;
    }
}
