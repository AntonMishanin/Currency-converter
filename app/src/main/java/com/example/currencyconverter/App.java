package com.example.currencyconverter;

import android.app.Application;

import com.example.currencyconverter.di.component.AppComponent;
import com.example.currencyconverter.di.component.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
