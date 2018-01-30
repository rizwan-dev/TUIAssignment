package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.viewmodel.Response;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Rizwanul Haque
 */

public class HomeViewModel extends ViewModel {
    private CompositeDisposable mCompositeDisposable;
    private NetworkService mService;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();

    HomeViewModel(NetworkService networkService) {
        this.mService = networkService;
    }

    void requestRandomJoke() {
        Disposable disposable = mService.requestRandomJoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        jokeResponse -> response.setValue(Response.success(jokeResponse.getJokeData().getJoke())),
                        throwable -> response.setValue(Response.error(throwable))
                );
        disposables.add(disposable);
    }

    MutableLiveData<Response> response() {
        return response;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
