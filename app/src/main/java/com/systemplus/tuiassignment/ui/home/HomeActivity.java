package com.systemplus.tuiassignment.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity {
    @Inject
    NetworkService mNetworkService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDeps().inject(this);
    }
}
