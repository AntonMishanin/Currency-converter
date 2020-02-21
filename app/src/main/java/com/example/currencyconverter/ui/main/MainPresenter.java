package com.example.currencyconverter.ui.main;

public class MainPresenter implements OnValCursListener {

    private CurrencyConverter currencyConverter;
    private MainView mainView;

    public MainPresenter(CurrencyConverter currencyConverter, MainView mainView) {
        this.currencyConverter = currencyConverter;
        this.mainView = mainView;
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
}
