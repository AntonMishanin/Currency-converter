package com.example.currencyconverter.di.component;

import com.example.currencyconverter.di.PerActivity;
import com.example.currencyconverter.di.module.SplashModule;
import com.example.currencyconverter.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {

    void inject(SplashActivity splashActivity);
}
