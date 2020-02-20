package com.example.currencyconverter.di.module;

import com.example.currencyconverter.data.network.Api;
import com.example.currencyconverter.di.PerActivity;
import com.example.currencyconverter.ui.splash.Interactor;
import com.example.currencyconverter.ui.splash.MvpView;
import com.example.currencyconverter.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

@Module
public class SplashModule {

    private MvpView mvpView;

    public SplashModule(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @PerActivity
    @Provides
    Interactor provideInteractor() {
        return new Interactor();
    }

    @PerActivity
    @Provides
    SplashPresenter provideSplashPresenter(Interactor interactor) {
        return new SplashPresenter(interactor, mvpView);
    }

    @PerActivity
    @Provides
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
