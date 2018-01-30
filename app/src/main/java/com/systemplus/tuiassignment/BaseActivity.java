package com.systemplus.tuiassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.systemplus.tuiassignment.deps.DaggerDeps;
import com.systemplus.tuiassignment.deps.Deps;
import com.systemplus.tuiassignment.networking.NetworkModule;

import java.io.File;

import timber.log.Timber;


/**
 * Activity skeleton to let other  activities to extend from it. This
 * provides a single place to perform the tasks which was meant for all the
 * activities.
 *
 * @author Rizwanul Haque
 */


public class BaseActivity extends AppCompatActivity {
    private Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");

        // Building dependency module
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
        Timber.plant(new Timber.DebugTree());

    }

    protected Deps getDeps() {
        return deps;
    }

}