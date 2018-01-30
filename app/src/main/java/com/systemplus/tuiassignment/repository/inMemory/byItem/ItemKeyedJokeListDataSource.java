package com.systemplus.tuiassignment.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.systemplus.tuiassignment.model.JokeData;
import com.systemplus.tuiassignment.networking.NetworkError;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.repository.NetworkState;
import com.systemplus.tuiassignment.repository.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author Rizwanul Haque
 */

public class ItemKeyedJokeListDataSource extends ItemKeyedDataSource<Long, JokeData> {
    public static final String TAG = "ItemKeyedJokeListDataSource";
    private static final String SUCCESS = "success";
    private NetworkService mNetworkService;
    LoadInitialParams<Long> initialParams;
    LoadParams<Long> afterParams;
    private MutableLiveData mNetworkState;
    private MutableLiveData mInitialLoading;
    private Executor retryExecutor;

    public ItemKeyedJokeListDataSource(Executor retryExecutor, NetworkService networkService) {
        mNetworkService = networkService;
        mNetworkState = new MutableLiveData();
        mInitialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
    }


    public MutableLiveData getNetworkState() {
        return mNetworkState;
    }

    public MutableLiveData getInitialLoading() {
        return mInitialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<JokeData> callback) {
        List<JokeData> jokeDataList = new ArrayList();
        initialParams = params;

        mInitialLoading.postValue(NetworkState.LOADING);
        mNetworkState.postValue(NetworkState.LOADING);
        mNetworkService.requestJokeListWithPaging("20").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        jokeListResponse -> {
                            if (jokeListResponse.getType().equals(SUCCESS)) {
                                jokeDataList.addAll(jokeListResponse.getJokeData());
                                callback.onResult(jokeDataList);
                                mInitialLoading.postValue(NetworkState.LOADED);
                                mNetworkState.postValue(NetworkState.LOADED);
                                afterParams = null;
                            } else {
                                mInitialLoading.postValue(new NetworkState(Status.ERROR, jokeListResponse.getType()));
                                mNetworkState.postValue(new NetworkState(Status.ERROR, jokeListResponse.getType()));
                            }

                        },
                        throwable -> {
                            mNetworkState.postValue(new NetworkState(Status.ERROR, new NetworkError(throwable).getMessage()));
                            mInitialLoading.postValue(new NetworkState(Status.ERROR, new NetworkError(throwable).getMessage()));
                        }
                );

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<JokeData> callback) {
        List<JokeData> jokeDataList = new ArrayList();
        afterParams = params;


        mNetworkService.requestJokeListWithPaging("20").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> {
                    mNetworkState.postValue(NetworkState.LOADING);
                })
                .subscribe(
                        jokeListResponse -> {
                            if (jokeListResponse.getType().equals(SUCCESS)) {
                                jokeDataList.addAll(jokeListResponse.getJokeData());
                                callback.onResult(jokeDataList);
                                mNetworkState.postValue(NetworkState.LOADED);
                                afterParams = null;
                            } else {
                                mNetworkState.postValue(new NetworkState(Status.ERROR, jokeListResponse.getType()));
                            }

                        },
                        throwable -> mNetworkState.postValue(new NetworkState(Status.ERROR, new NetworkError(throwable).getMessage()))
                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<JokeData> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull JokeData item) {
        return item.getId();
    }

}
