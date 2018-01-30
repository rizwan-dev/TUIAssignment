package com.systemplus.tuiassignment.ui.neverendinglist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.systemplus.tuiassignment.networking.NetworkService;

public class JokeListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NetworkService mNetworkService;


    JokeListViewModelFactory(NetworkService networkService) {
        this.mNetworkService = networkService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new JokeListViewModel(mNetworkService);
    }
}