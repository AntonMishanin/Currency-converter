package com.example.currencyconverter.di.component;

import com.example.currencyconverter.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Retrofit getRetrofit();
}
