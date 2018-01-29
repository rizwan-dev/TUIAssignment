package com.systemplus.tuiassignment.networking;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("message")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
