package com.example.currencyconverter;

public class MainPresenter implements FetchData {

    Interactor interactor;
    MvpView mvpView;

    public MainPresenter(Interactor interactor, MvpView mvpView) {
        this.interactor = interactor;
        this.mvpView = mvpView;
    }

    void startDownload() {
        interactor.downloadValCurs(this);
    }

    @Override
    public void fetchData(ValCurs valCurs) {
        mvpView.showData(valCurs);
    }

    @Override
    public void error(Throwable e) {
        mvpView.showError();
    }
}
