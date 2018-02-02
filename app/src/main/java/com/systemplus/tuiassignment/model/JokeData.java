package com.systemplus.tuiassignment.model;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used for storing Joke details
 * @author Rizwanul haque
 */
public class JokeData {

    public static DiffCallback<JokeData> DIFF_CALLBACK = new DiffCallback<JokeData>() {
        @Override
        public boolean areItemsTheSame(@NonNull JokeData oldItem, @NonNull JokeData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull JokeData oldItem, @NonNull JokeData newItem) {
            return oldItem.equals(newItem);
        }
    };

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("joke")
    @Expose
    private String joke;

    public JokeData(long id, String joke) {
        this.id = id;
        this.joke = joke;
    }

    public JokeData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        JokeData user = (JokeData) obj;

        return user.getId() == this.getId() && user.getJoke() == this.getJoke();
    }

}