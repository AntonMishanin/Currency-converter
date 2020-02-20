package com.example.currencyconverter.ui.splash;

import com.example.currencyconverter.data.network.Api;

public interface MvpInteractor {

    void downloadValCurs(FetchData fetchData, Api api);
}
