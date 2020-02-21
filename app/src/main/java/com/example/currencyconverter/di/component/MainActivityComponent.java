package com.example.currencyconverter.di.component;

import com.example.currencyconverter.di.PerActivity;
import com.example.currencyconverter.di.module.MainActivityModule;
import com.example.currencyconverter.ui.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

   void inject(MainActivity mainActivity);
}
