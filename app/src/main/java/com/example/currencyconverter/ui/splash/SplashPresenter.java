package com.example.currencyconverter.ui.splash;

import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.data.network.Api;

public class SplashPresenter implements FetchData {

    private Interactor interactor;
    private MvpView mvpView;

    public SplashPresenter(Interactor interactor, MvpView mvpView) {
        this.interactor = interactor;
        this.mvpView = mvpView;
    }

    void startDownload(Api api) {
        interactor.downloadValCurs(this, api);
    }

    @Override
    public void fetchData(ValCurs valCurs) {
        mvpView.showData(valCurs);
    }

    @Override
    public void error(Throwable e) {
        mvpView.showError(e);
    }
}
