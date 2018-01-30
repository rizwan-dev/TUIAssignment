package com.systemplus.tuiassignment.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JokeListResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private List<JokeData> jokeData = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<JokeData> getJokeData() {
        return jokeData;
    }

    public void setJokeData(List<JokeData> jokeData) {
        this.jokeData = jokeData;
    }

}