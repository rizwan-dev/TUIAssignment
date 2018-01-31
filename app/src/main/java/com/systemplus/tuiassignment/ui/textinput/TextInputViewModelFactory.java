package com.systemplus.tuiassignment.ui.textinput;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.ui.home.HomeViewModel;

public class TextInputViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NetworkService mNetworkService;


    TextInputViewModelFactory(NetworkService networkService) {
        this.mNetworkService = networkService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TextInputViewModel(mNetworkService);
    }
}