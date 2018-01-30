package com.systemplus.tuiassignment.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.systemplus.tuiassignment.networking.NetworkService;

import java.util.concurrent.Executor;

/**
 * @author Rizwanul Haque
 */

public class JokeListDataSourceFactory implements DataSource.Factory {

    MutableLiveData<ItemKeyedJokeListDataSource> mutableLiveData;
    ItemKeyedJokeListDataSource itemKeyedJokeListDataSource;
    Executor executor;
    NetworkService mNetworkService;

    public JokeListDataSourceFactory(Executor executor, NetworkService networkService) {
        this.mutableLiveData = new MutableLiveData<ItemKeyedJokeListDataSource>();
        this.executor = executor;
        this.mNetworkService = networkService;
    }


    @Override
    public DataSource create() {
        itemKeyedJokeListDataSource = new ItemKeyedJokeListDataSource(executor, mNetworkService);
        mutableLiveData.postValue(itemKeyedJokeListDataSource);
        return itemKeyedJokeListDataSource;
    }

    public MutableLiveData<ItemKeyedJokeListDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}
