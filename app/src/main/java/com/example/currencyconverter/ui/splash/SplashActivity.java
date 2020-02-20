package com.example.currencyconverter.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.currencyconverter.R;
import com.example.currencyconverter.network.model.ValCurs;
import com.example.currencyconverter.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements MvpView {

    public static final String TAG = "SplashActivity";

    MainPresenter mainPresenter;
    Interactor interactor;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    void init() {
        interactor = new Interactor();
        mainPresenter = new MainPresenter(interactor, this);
        progressBar = findViewById(R.id.progressBar);
        mainPresenter.startDownload();
    }

    @Override
    public void showError(Throwable e) {
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
