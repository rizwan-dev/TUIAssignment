package com.systemplus.tuiassignment.networking;

import com.systemplus.tuiassignment.model.JokeListResponse;
import com.systemplus.tuiassignment.model.RandomJokeResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Rizwanul Haque
 */

public interface NetworkService {
    //Get random joke
    @GET("jokes/random")
    Observable<RandomJokeResponse> requestRandomJoke(@Query("exclude") String excludedCategories);

    //Get joke with custom character
    @GET("jokes/random")
    Observable<RandomJokeResponse> requestJokeWithName(@Query("firstName") String firstName, @Query("lastName") String lastName, @Query("exclude") String excludedCategories);

    //Get joke list in pages
    @GET("jokes/random/{pageSize}")
    Observable<JokeListResponse> requestJokeListWithPaging(@Path("pageSize") int pageSize, @Query("exclude") String excludedCategories);

}
