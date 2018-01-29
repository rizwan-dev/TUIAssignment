package com.systemplus.tuiassignment.deps;

import com.systemplus.tuiassignment.networking.NetworkModule;
import com.systemplus.tuiassignment.ui.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Rizwanul Haque
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface Deps {
    
    /**
     * This Method Injects the Dependencies in Defined Activities.
     *
     * @param activity
     */
    void inject(HomeActivity activity);
    

}