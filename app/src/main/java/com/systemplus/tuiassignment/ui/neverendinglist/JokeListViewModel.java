package com.systemplus.tuiassignment.ui.neverendinglist;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.systemplus.tuiassignment.model.JokeData;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.repository.NetworkState;
import com.systemplus.tuiassignment.repository.inMemory.byItem.ItemKeyedJokeListDataSource;
import com.systemplus.tuiassignment.repository.inMemory.byItem.JokeListDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Rizwanul Haque
 */

public class JokeListViewModel extends ViewModel {

    LiveData<PagedList<JokeData>> mJokeDataList;
    LiveData<NetworkState> mNetworkState;
    LiveData<NetworkState> mInitialLoading;
    private Executor mExecutor;
    private LiveData<ItemKeyedJokeListDataSource> mJokeListDataSource;

    public JokeListViewModel(NetworkService networkService){
        mExecutor = Executors.newFixedThreadPool(5);
        JokeListDataSourceFactory jokeListDataSourceFactory = new JokeListDataSourceFactory(mExecutor, networkService);

        mJokeListDataSource = jokeListDataSourceFactory.getMutableLiveData();

        mNetworkState = Transformations.switchMap(jokeListDataSourceFactory.getMutableLiveData(), (Function<ItemKeyedJokeListDataSource, LiveData<NetworkState>>) ItemKeyedJokeListDataSource::getNetworkState);

        mInitialLoading = Transformations.switchMap(jokeListDataSourceFactory.getMutableLiveData(), (Function<ItemKeyedJokeListDataSource, LiveData<NetworkState>>) ItemKeyedJokeListDataSource::getInitialLoading);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        mJokeDataList = (new LivePagedListBuilder(jokeListDataSourceFactory, pagedListConfig))
                .setBackgroundThreadExecutor(mExecutor)
                .build();
    }

}
