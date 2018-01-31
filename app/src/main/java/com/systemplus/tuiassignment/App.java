package com.systemplus.tuiassignment;

import android.app.Application;

import com.tumblr.remember.Remember;

import timber.log.Timber;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Remember.init(getApplicationContext(), getString(R.string.app_name));
        plantTimber();
    }

    private void plantTimber() {
        Timber.uprootAll();
        Timber.plant(new Timber.DebugTree());
    }


}
