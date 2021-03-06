package com.systemplus.tuiassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RandomJokeResponse {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private JokeData jokeData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JokeData getJokeData() {
        return jokeData;
    }

    public void setJokeData(JokeData jokeData) {
        this.jokeData = jokeData;
    }

}