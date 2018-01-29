package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.systemplus.tuiassignment.model.RandomJokeResponse;
import com.systemplus.tuiassignment.networking.NetworkError;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.viewmodel.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author Rizwanul Haque
 */

public class RandomJokeViewModel extends ViewModel {
    private CompositeDisposable mCompositeDisposable;
    private NetworkService mService;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();

    public void requestRandomJoke(NetworkService mService) {
        Disposable disposable = mService.requestRandomJoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        jokeResponse -> response.setValue(Response.success(jokeResponse.getValue().getJoke())),
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
