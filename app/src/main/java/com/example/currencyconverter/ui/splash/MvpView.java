package com.example.currencyconverter.ui.splash;

import com.example.currencyconverter.data.model.ValCurs;

public interface MvpView {

    void showData(ValCurs valCurs);
    void showError(Throwable e);
}
