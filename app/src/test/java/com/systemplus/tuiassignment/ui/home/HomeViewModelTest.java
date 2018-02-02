package com.systemplus.tuiassignment.ui.home;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.systemplus.tuiassignment.model.JokeData;
import com.systemplus.tuiassignment.model.RandomJokeResponse;
import com.systemplus.tuiassignment.networking.NetworkServiceImpl;
import com.systemplus.tuiassignment.util.SPConstants;
import com.systemplus.tuiassignment.util.TUIUtil;
import com.tumblr.remember.Remember;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Performs unit testing for HomeViewModel
 * @author Rizwanul Haque
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Remember.class)
public class HomeViewModelTest {

    @Mock
    NetworkServiceImpl mockNetworkServiceImpl;


    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();
    private HomeViewModel viewModel;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Remember.class);
        viewModel = new HomeViewModel(mockNetworkServiceImpl);
    }


    @Test
    public void requestRandomJoke_successs() throws Exception {
        //TODO: Need to handle success case correctly
        RandomJokeResponse randomJokeResponse = new RandomJokeResponse();
        randomJokeResponse.setJokeData(new JokeData(112, "Dummy joke string for test"));
        when(mockNetworkServiceImpl.requestRandomJoke(TUIUtil.getCategoryToExclude()))
                .thenReturn(Observable.just(randomJokeResponse));
        when(Remember.getBoolean(SPConstants.IS_EXCLUDE_EXPLICIT, false)).thenReturn(false);
        viewModel.requestRandomJoke();
        verify(viewModel.response());
    }

    @Test
    public void requestRandomJoke_error() throws Exception {
        //TODO: Need to verify error case

    }

    @Test
    public void requestRandomJoke_networkError() throws Exception {
        //TODO: Need to verify network error case

    }

}