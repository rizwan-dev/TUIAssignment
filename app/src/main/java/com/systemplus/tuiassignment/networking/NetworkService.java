package com.systemplus.tuiassignment.networking;

import com.systemplus.tuiassignment.model.RandomJokeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * @author Rizwanul Haque
 */

public interface NetworkService {
    //Get referral code
    @GET("jokes/random")
    Observable<RandomJokeResponse> requestRandomJoke();
}
