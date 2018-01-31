package com.systemplus.tuiassignment.ui.textinput;

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
 * @author Rizwanul Haque
 */

public class TextInputViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    boolean isJokeAlreadyDisplayed;
    private NetworkService mNetworkService;

    public TextInputViewModel(NetworkService networkService) {
        this.mNetworkService = networkService;
    }

    public void requestRandomJoke(String firstName, String lastName) {
        isJokeAlreadyDisplayed = false;
        Disposable disposable = mNetworkService.requestJokeWithName(firstName, lastName, TUIUtil.getCategoryToExclude()).subscribeOn(Schedulers.io())
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
