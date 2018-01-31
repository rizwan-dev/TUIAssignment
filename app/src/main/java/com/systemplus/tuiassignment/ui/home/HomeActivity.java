package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.ui.neverendinglist.NeverEndingListActivity;
import com.systemplus.tuiassignment.ui.textinput.TextInputActivity;
import com.systemplus.tuiassignment.util.JokeDisplayDialogFragment;
import com.systemplus.tuiassignment.repository.Response;
import com.systemplus.tuiassignment.util.SPConstants;
import com.tumblr.remember.Remember;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.systemplus.tuiassignment.util.TUIConstants.DIALOG_TAG;
import static com.systemplus.tuiassignment.util.TUIConstants.MESSAGE_BODY;

public class HomeActivity extends BaseActivity {
    @Inject
    NetworkService mNetworkService;
    private HomeViewModel mViewModel;
    FragmentManager mFragmentManager = getSupportFragmentManager();

    @BindView(R.id.progress)
    ProgressBar mProgress;

    @BindView(R.id.cbExcludeExplicit)
    CheckBox cbExcludeExplicit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDeps().inject(this);
        init();

        mViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(mNetworkService)).get(HomeViewModel.class);
        mViewModel.response().observe(this, this::processResponse);
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.home));
        boolean isExcludedExplicit = Remember.getBoolean(SPConstants.IS_EXCLUDE_EXPLICIT, false);
        cbExcludeExplicit.setChecked(isExcludedExplicit);

        cbExcludeExplicit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Remember.putBoolean(SPConstants.IS_EXCLUDE_EXPLICIT,isChecked);
            }
        });

    }


    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                mProgress.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                if (!mViewModel.isJokeAlreadyDisplayed) {
                    mProgress.setVisibility(View.GONE);
                    showJokeDialog(response.data);
                }
                break;

            case ERROR:
                mProgress.setVisibility(View.GONE);
                showToast(response.error != null ? response.error.getMessage() : getString(R.string.default_error_message));
                break;
        }
    }

    @OnClick({R.id.btnNeverEndingList, R.id.btnRandomJoke, R.id.btnTextInput})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnTextInput:
                startActivity(new Intent(this, TextInputActivity.class));
                break;
            case R.id.btnRandomJoke:
                requestRandomJoke();
                break;
            case R.id.btnNeverEndingList:
                startActivity(new Intent(this, NeverEndingListActivity.class));
                break;
        }
    }

    private void requestRandomJoke() {
        mViewModel.requestRandomJoke();
    }

    public void showJokeDialog(String message) {
        mViewModel.isJokeAlreadyDisplayed = true;
        JokeDisplayDialogFragment jokeDialogFragment = JokeDisplayDialogFragment.newInstance(message);
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_BODY, message);
        jokeDialogFragment.setArguments(bundle);
        jokeDialogFragment.show(mFragmentManager, DIALOG_TAG);
    }


}
