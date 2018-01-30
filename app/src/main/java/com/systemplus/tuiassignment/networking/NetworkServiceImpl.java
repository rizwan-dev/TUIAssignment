package com.systemplus.tuiassignment.networking;

import com.systemplus.tuiassignment.model.JokeListResponse;
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
    public Observable<RandomJokeResponse> requestJokeWithName(String firstName, String lastName) {
        return mNetworkService.requestJokeWithName(firstName, lastName);
    }

    @Override
    public Observable<JokeListResponse> requestJokeListWithPaging(String pageSize) {
        return mNetworkService.requestJokeListWithPaging(pageSize);
    }

}
