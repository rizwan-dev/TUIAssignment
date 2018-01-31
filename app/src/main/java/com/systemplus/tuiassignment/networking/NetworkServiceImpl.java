package com.systemplus.tuiassignment.networking;

import com.systemplus.tuiassignment.model.JokeListResponse;
import com.systemplus.tuiassignment.model.RandomJokeResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Rizwanul Haque
 */

public class NetworkServiceImpl implements NetworkService {

    private final NetworkService mNetworkService;

    NetworkServiceImpl(NetworkService mNetworkService) {
        this.mNetworkService = mNetworkService;
    }

    @Override
    public Observable<RandomJokeResponse> requestRandomJoke(String excludedCategories) {
        return mNetworkService.requestRandomJoke(excludedCategories);
    }

    @Override
    public Observable<RandomJokeResponse> requestJokeWithName(String firstName, String lastName, String excludedCategories) {
        return mNetworkService.requestJokeWithName(firstName, lastName, excludedCategories);
    }

    @Override
    public Observable<JokeListResponse> requestJokeListWithPaging(int pageSize, String excludedCategories) {
        return mNetworkService.requestJokeListWithPaging(pageSize, excludedCategories);
    }

}
