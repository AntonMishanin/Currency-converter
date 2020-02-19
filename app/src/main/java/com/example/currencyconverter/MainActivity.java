package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Api api;

    Dialog dialog;

    ValCurs valCurs;
    AdapterMainActivity adapterMainActivity;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            valCurs = (ValCurs) arguments.getSerializable(MainActivity.class.getSimpleName());
        }

        view = getLayoutInflater().inflate(R.layout.select_currency_layout, null);
        dialog = new Dialog(this, R.style.AppTheme);
        dialog.setContentView(view);


        init();

    }

    private void init() {
    /*    RetrofitClient.getApi().getValCurs()
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
                */
        adapterMainActivity = new AdapterMainActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMainActivity);
    }

    public void onChangeFirstVal(View view) {
        // SelectCurrencyFragment dialog = new SelectCurrencyFragment();
        // dialog.show(getSupportFragmentManager(), "custom");
        adapterMainActivity.setValute(valCurs);
        dialog.show();
    }

    public void onChangeSecondVal(View view) {
    }
}
