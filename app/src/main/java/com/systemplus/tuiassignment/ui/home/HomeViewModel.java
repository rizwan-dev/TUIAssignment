package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.repository.Response;
import com.systemplus.tuiassignment.util.TUIUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Responsible for managing data for HomeActivity
 * @author Rizwanul Haque
 */

public class HomeViewModel extends ViewModel {
    private NetworkService mService;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private final MutableLiveData<Response> mResponse = new MutableLiveData<>();
    boolean isJokeAlreadyDisplayed;

    HomeViewModel(NetworkService networkService) {
        this.mService = networkService;
    }

    /**
     * Performs server call for fetching random joke
     */
    void requestRandomJoke() {
        isJokeAlreadyDisplayed = false;
        Disposable disposable = mService.requestRandomJoke(TUIUtil.getCategoryToExclude()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> mResponse.setValue(Response.loading()))
                .subscribe(
                        jokeResponse -> mResponse.setValue(Response.success(jokeResponse.getJokeData().getJoke())),
                        throwable -> mResponse.setValue(Response.error(throwable))
                );
        mDisposables.add(disposable);
    }

    MutableLiveData<Response> response() {
        return mResponse;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

}
