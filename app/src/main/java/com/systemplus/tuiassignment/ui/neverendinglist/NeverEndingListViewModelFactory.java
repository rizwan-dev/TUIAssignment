package com.systemplus.tuiassignment.ui.neverendinglist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.systemplus.tuiassignment.networking.NetworkService;

public class NeverEndingListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NetworkService mNetworkService;


    /**
     * Creates NeverEndingListViewModelFactory which will be used for creating NeverEndingListViewModel
     * @param networkService
     */
    NeverEndingListViewModelFactory(NetworkService networkService) {
        this.mNetworkService = networkService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NeverEndingListViewModel(mNetworkService);
    }
}