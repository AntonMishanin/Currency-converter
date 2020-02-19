package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity implements MvpView {

    MainPresenter mainPresenter;
    Interactor interactor;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);

        init();
        mainPresenter.startDownload();
    }

    void init() {
        interactor = new Interactor();
        mainPresenter = new MainPresenter(interactor, this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showData(ValCurs valCurs) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.class.getSimpleName(), valCurs);
        startActivity(intent);
        finish();
    }
}
