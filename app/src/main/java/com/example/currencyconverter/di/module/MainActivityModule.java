package com.example.currencyconverter.di.module;

import android.content.SharedPreferences;

import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.data.prefs.PreferencesHelper;
import com.example.currencyconverter.di.PerActivity;
import com.example.currencyconverter.ui.main.CurrencyConverter;
import com.example.currencyconverter.ui.main.MainPresenter;
import com.example.currencyconverter.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private SharedPreferences sharedPreferences;
    private ValCurs valCurs;
    private MainView mainView;

    public MainActivityModule(SharedPreferences sharedPreferences, ValCurs valCurs, MainView mainView) {
        this.sharedPreferences = sharedPreferences;
        this.valCurs = valCurs;
        this.mainView = mainView;
    }

    @PerActivity
    @Provides
    PreferencesHelper providePreferencesHelper(){
        return new PreferencesHelper(sharedPreferences);
    }

    @PerActivity
    @Provides
    CurrencyConverter provideCurrencyConverter(){
        return new CurrencyConverter(valCurs);
    }

    @PerActivity
    @Provides
    MainPresenter provideMainPresenter(CurrencyConverter currencyConverter, PreferencesHelper preferencesHelper){
        return new MainPresenter(currencyConverter, mainView, preferencesHelper);
    }

}
