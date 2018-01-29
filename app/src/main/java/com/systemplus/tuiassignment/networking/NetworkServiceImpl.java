package com.systemplus.tuiassignment.networking;

import android.arch.lifecycle.LiveData;

import com.systemplus.tuiassignment.model.BaseResponse;
import com.systemplus.tuiassignment.model.RandomJokeResponse;

import io.reactivex.Observable;

/**
 * @author Rizwanul Haque
 */

public class NetworkServiceImpl implements NetworkService{

    private final NetworkService mNetworkService;

    public NetworkServiceImpl(NetworkService mNetworkService) {
        this.mNetworkService = mNetworkService;
    }

    @Override
    public Observable<RandomJokeResponse> requestRandomJoke() {
        return mNetworkService.requestRandomJoke();
    }

    @Override
    public Observable<BaseResponse> requestRandomJokeTest(String s) {
        return mNetworkService.requestRandomJokeTest(s);
    }
}
