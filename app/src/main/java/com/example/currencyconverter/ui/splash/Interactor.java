package com.example.currencyconverter.ui.splash;

import com.example.currencyconverter.data.network.Api;
import com.example.currencyconverter.data.model.ValCurs;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Interactor implements MvpInteractor {


    @Override
    public void downloadValCurs(FetchData fetchData, Api api) {
        api.getValCurs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ValCurs>() {
                    @Override
                    public void onSuccess(ValCurs valCurs) {

                        fetchData.fetchData(valCurs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        fetchData.error(e);
                    }
                });
    }
}
