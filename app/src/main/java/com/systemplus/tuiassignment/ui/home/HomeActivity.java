package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.viewmodel.Response;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @Inject
    NetworkService mNetworkService;
    private RandomJokeViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDeps().inject(this);
        mViewModel = ViewModelProviders.of(this).get(RandomJokeViewModel.class);
    }



    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                Toast.makeText(this, "Start Loading", Toast.LENGTH_SHORT).show();
                break;

            case SUCCESS:
                Toast.makeText(this, response.data, Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                Toast.makeText(this, response.error.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @OnClick({R.id.btnNeverEndingList,R.id.btnRandomJoke,R.id.btnTextInput})
    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.btnTextInput:
                break;
            case R.id.btnRandomJoke:
                requestRandomJoke();
                break;
            case R.id.btnNeverEndingList:
                break;
        }
    }

    private void requestRandomJoke() {
        mViewModel.requestRandomJoke(mNetworkService);
        mViewModel.response().observe(this, response -> processResponse(response));
    }
}
