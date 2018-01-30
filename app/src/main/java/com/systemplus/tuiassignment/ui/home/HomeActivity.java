package com.systemplus.tuiassignment.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.ui.neverendinglist.JokeListViewModel;
import com.systemplus.tuiassignment.ui.neverendinglist.JokeListViewModelFactory;
import com.systemplus.tuiassignment.ui.neverendinglist.NeverEndingListActivity;
import com.systemplus.tuiassignment.ui.textinput.TextInputActivity;
import com.systemplus.tuiassignment.util.JokeDisplayDialogFragment;
import com.systemplus.tuiassignment.viewmodel.Response;

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
    FragmentManager fm = getSupportFragmentManager();

    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDeps().inject(this);
        mViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(mNetworkService)).get(HomeViewModel.class);
        mViewModel.response().observe(this, this::processResponse);
    }


    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                progress.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                progress.setVisibility(View.GONE);
                showJokeDialog(response.data);
                Toast.makeText(this, response.data, Toast.LENGTH_SHORT).show();
                break;

            case ERROR:
                progress.setVisibility(View.GONE);
                Toast.makeText(this, response.error.getMessage(), Toast.LENGTH_SHORT).show();
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
        Fragment prevJokeDialogFragment = fm.findFragmentByTag(DIALOG_TAG);
        if (prevJokeDialogFragment == null) {
            JokeDisplayDialogFragment jokeDialogFragment = JokeDisplayDialogFragment.newInstance(message);
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE_BODY, message);
            jokeDialogFragment.setArguments(bundle);
            // Show DialogFragment
            jokeDialogFragment.show(fm, DIALOG_TAG);
        }
    }


}
