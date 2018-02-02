package com.systemplus.tuiassignment.ui.neverendinglist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.repository.Status;
import com.systemplus.tuiassignment.util.ListItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class NeverEndingListActivity extends BaseActivity implements ListItemClickListener {
    @Inject
    NetworkService mNetworkService;

    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_never_ending_list);
        ButterKnife.bind(this);
        getDeps().inject(this);
        RecyclerView jokeList = findViewById(R.id.jokeList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        jokeList.setLayoutManager(llm);
        jokeList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        NeverEndingListViewModel mViewModel = ViewModelProviders.of(this, new NeverEndingListViewModelFactory(mNetworkService)).get(NeverEndingListViewModel.class);

        final JokeListAdapter jokeListAdapter = new JokeListAdapter(this);

        mViewModel.mJokeDataList.observe(this, jokeListAdapter::setList);


        mViewModel.mNetworkState.observe(this, networkState -> {
            jokeListAdapter.setNetworkState(networkState);
            Timber.d("Network State Change");
        });
        mViewModel.mInitialLoading.observe(this, networkState -> {
            if ((networkState != null ? networkState.getStatus() : null) == Status.SUCCESS) {
                progress.setVisibility(View.GONE);
            } else if ((networkState != null ? networkState.getStatus() : null) == Status.LOADING) {
                progress.setVisibility(View.VISIBLE);
            }
        });

        jokeList.setAdapter(jokeListAdapter);


    }

    @Override
    public void onRetryClick(View view, int position) {

    }
}
