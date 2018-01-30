package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.ui.neverendinglist.JokeListViewModel;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NetworkService mNetworkService;


    HomeViewModelFactory(NetworkService networkService) {
        this.mNetworkService = networkService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(mNetworkService);
    }
}