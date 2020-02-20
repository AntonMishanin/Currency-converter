package com.example.currencyconverter.ui.splash;

import com.example.currencyconverter.network.model.ValCurs;

public interface FetchData {

    void fetchData(ValCurs valCurs);

    void error(Throwable e);
}
