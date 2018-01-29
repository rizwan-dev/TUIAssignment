package com.systemplus.tuiassignment.networking;

/**
 * @author Rizwanul Haque
 */


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.systemplus.tuiassignment.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


@Module
public class NetworkModule {

    File cacheFile;

    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {

        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 10 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String userAgent = System.getProperty("http.agent");
    
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                    .header("Content-Type", "application/json")
                                    .header("User-Agent", userAgent)
                                    .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME)).build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .readTimeout(BuildConfig.READTIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(BuildConfig.CONNECTIONTIMEOUT,TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }


    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkServiceImpl providesNetworkServiceImpl(
            NetworkService networkService) {
        return new NetworkServiceImpl(networkService);
    }

}