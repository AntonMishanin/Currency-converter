package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        RetrofitClient.getApi().getValCurs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ValCurs>() {
                    @Override
                    public void onSuccess(ValCurs valCurs) {
                        Log.d(TAG, "Size: " + valCurs.getValuteList().get(1).getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error: "+e.getMessage());
                    }
                });
    }
}
