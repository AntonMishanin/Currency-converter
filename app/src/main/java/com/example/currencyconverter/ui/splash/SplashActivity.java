package com.example.currencyconverter.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.currencyconverter.App;
import com.example.currencyconverter.R;
import com.example.currencyconverter.data.model.MyValute;
import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.data.network.Api;
import com.example.currencyconverter.di.component.AppComponent;
import com.example.currencyconverter.di.component.DaggerSplashComponent;
import com.example.currencyconverter.di.module.SplashModule;
import com.example.currencyconverter.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements MvpView {

    public static final String TAG = "SplashActivity";

    @Inject
    SplashPresenter splashPresenter;
    @Inject
    Api api;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initDagger();
        init();
    }

    private void init() {

        progressBar = findViewById(R.id.progressBar);
        splashPresenter.startDownload(api);

        Toolbar toolbar = findViewById(R.id.toolbar_splash);
        setSupportActionBar(toolbar);
    }

    private void initDagger() {
        DaggerSplashComponent.builder()
                .appComponent(getAppComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    private AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    @Override
    public void showError(Throwable e) {
        List<MyValute> valList = new ArrayList<>();

        MyValute testValute = new MyValute();
        testValute.setName("Рубли");
        testValute.setCharCode("TEST");
        testValute.setNominal("1");
        testValute.setValue("3");

        MyValute testValute2 = new MyValute();
        testValute2.setName("Увстралийская валюта текст очень длинный тестируем");
        testValute2.setCharCode("TEST2");
        testValute2.setNominal("1");
        testValute2.setValue("6");

        valList.add(testValute);
        valList.add(testValute2);

        ValCurs valCurs = new ValCurs();
        valCurs.setValuteList(new ArrayList<>());
        valCurs.getValuteList().addAll(valList);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.class.getSimpleName(), valCurs);
        startActivity(intent);

        Log.d(TAG, e.getMessage());
    }

    @Override
    public void showData(ValCurs valCurs) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.class.getSimpleName(), valCurs);
        startActivity(intent);
        finish();
    }
}
