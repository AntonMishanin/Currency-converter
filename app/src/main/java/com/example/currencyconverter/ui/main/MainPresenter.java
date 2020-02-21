package com.example.currencyconverter.ui.main;

import com.example.currencyconverter.data.prefs.PrefModel;
import com.example.currencyconverter.data.prefs.PreferencesHelper;

public class MainPresenter implements OnValCursListener {

    private CurrencyConverter currencyConverter;
    private MainView mainView;
    private PreferencesHelper preferencesHelper;

    public MainPresenter(CurrencyConverter currencyConverter, MainView mainView, PreferencesHelper preferencesHelper) {
        this.currencyConverter = currencyConverter;
        this.mainView = mainView;
        this.preferencesHelper = preferencesHelper;
    }

    void startConverter(String currentValuteValue, String currentChatCode, String secondCharCode) {
        currencyConverter.textChanged(currentValuteValue, currentChatCode, secondCharCode, this);
    }

    @Override
    public void onSuccess(String valCurs) {
        mainView.setValCurs(valCurs);
    }

    @Override
    public void onInputError(String message) {
        mainView.showInputError(message);
    }

    void setSharedPreferences(PrefModel prefModel) {
        preferencesHelper.setSharedPreferences(prefModel);
    }

    PrefModel getSharedPreferences() {
        return preferencesHelper.getSharedPreferences();
    }
}
