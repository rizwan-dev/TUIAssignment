package com.systemplus.tuiassignment.networking;

import android.arch.lifecycle.LiveData;

import com.systemplus.tuiassignment.model.BaseResponse;
import com.systemplus.tuiassignment.model.RandomJokeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @author Rizwanul Haque
 */

public interface NetworkService {
    //Get random joke
    @GET("jokes/random")
    Observable<RandomJokeResponse> requestRandomJoke();

    @GET
    Observable<BaseResponse> requestRandomJokeTest(@Url String s);


}
