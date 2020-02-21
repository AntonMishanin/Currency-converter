package com.example.currencyconverter.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.currencyconverter.R;
import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.ui.main.adapter.AdapterMainActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener, MainView {

    Dialog dialog;

    ValCurs valCurs;
    AdapterMainActivity adapterMainActivity;
    View view;

    EditText firstValuteValueText;
    EditText secondValuteValueText;

    TextView firstCharCodeText;
    TextView secondCharCodeText;

    boolean firstButtonPressed = true;

    TextWatcher firstTextWatcher;
    TextWatcher secondTextWatcher;
    boolean firstValueEditedNow = false;
    boolean secondValueEditedNow = false;

    CurrencyConverter currencyConverter;
    MainPresenter mainPresenter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        getValCursFromSplash();
        initDialog();
        initView();
        initTextWatcher();

        currencyConverter = new CurrencyConverter(valCurs);
        mainPresenter = new MainPresenter(currencyConverter, this);

        // firstCharCodeText.setText(valCurs.getValuteList().get(0).getCharCode());
        //secondCharCodeText.setText(valCurs.getValuteList().get(1).getCharCode());
        //firstValuteValueText.setText("1");

        // firstValueEditedNow = true;
        // textChanged(firstValuteValueText, firstCharCodeText, secondValuteValueText, secondCharCodeText);
        // firstValueEditedNow = false;

        firstValuteValueText.addTextChangedListener(firstTextWatcher);
        secondValuteValueText.addTextChangedListener(secondTextWatcher);

        init();

        loadSharedPreferences();


    }

    public void onClickToolbarCancel(View view) {
        dialog.dismiss();
    }

    void getValCursFromSplash() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            valCurs = (ValCurs) arguments.getSerializable(MainActivity.class.getSimpleName());
        }
    }

    @SuppressLint("InflateParams")
    void initDialog() {
        view = getLayoutInflater().inflate(R.layout.select_currency_layout, null);
        dialog = new Dialog(this, R.style.AppTheme);
        dialog.setContentView(view);
    }

    void initView() {
        firstValuteValueText = findViewById(R.id.valute_value_left);
        secondValuteValueText = findViewById(R.id.valute_value_right);
        firstCharCodeText = findViewById(R.id.valute_char_code_first);
        secondCharCodeText = findViewById(R.id.valute_char_code_second);
    }

    void initTextWatcher() {
        firstTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!secondValueEditedNow) {
                    firstValueEditedNow = true;
                    textChanged(firstValuteValueText, firstCharCodeText, secondValuteValueText, secondCharCodeText);
                    firstValueEditedNow = false;
                }
            }
        };

        secondTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!firstValueEditedNow) {
                    secondValueEditedNow = true;
                    textChanged(secondValuteValueText, secondCharCodeText, firstValuteValueText, firstCharCodeText);
                    secondValueEditedNow = false;
                }
            }
        };
    }

    void textChanged(EditText currentEditText, TextView currentCharCodeView, EditText secondEditText, TextView secondCharCodeView) {
        String currentValuteValue = currentEditText.getText().toString();
        String secondCharCode = secondCharCodeView.getText().toString();
        String currentChatCode = currentCharCodeView.getText().toString();

        mainPresenter.startConverter(currentValuteValue, currentChatCode, secondCharCode);


    }

    private void init() {
        adapterMainActivity = new AdapterMainActivity(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMainActivity);
    }

    public void onClickChangeFirstVal(View view) {
        firstButtonPressed = true;

        String firstCharCode = firstCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, firstCharCode);
        Log.d("dd", firstCharCode + "==============");
        dialog.show();
    }

    public void onClickChangeSecondVal(View view) {
        firstButtonPressed = false;

        String secondCharCode = secondCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, secondCharCode);
        Log.d("dd", secondCharCode + "==========");
        dialog.show();
    }

    private void saveSharedPreferences() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("First CharCode", firstCharCodeText.getText().toString());
        editor.putString("Second CharCode", secondCharCodeText.getText().toString());
        editor.putString("First Valute Value Text", firstValuteValueText.getText().toString());
        // editor.putString("Second Valute Value Text", secondValuteValueText.getText().toString());
        editor.putBoolean("Dialog show", dialog.isShowing());
        editor.putBoolean("First Button Pressed", firstButtonPressed);
        editor.apply();
    }

    private void loadSharedPreferences() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        firstCharCodeText.setText(sharedPreferences.getString("First CharCode", "USD"));
        secondCharCodeText.setText(sharedPreferences.getString("Second CharCode", "EUR"));
        firstValuteValueText.setText(sharedPreferences.getString("First Valute Value Text", "1"));

        secondValueEditedNow = true;
        textChanged(secondValuteValueText, secondCharCodeText, firstValuteValueText, firstCharCodeText);
        secondValueEditedNow = false;

        if (sharedPreferences.getBoolean("Dialog show", false)) {
            if (sharedPreferences.getBoolean("First Button Pressed", true)) {
                String firstCharCode = secondCharCodeText.getText().toString();
                adapterMainActivity.setValute(valCurs, firstCharCode);
                dialog.show();
            } else {
                String secondCharCode = secondCharCodeText.getText().toString();
                adapterMainActivity.setValute(valCurs, secondCharCode);
                dialog.show();
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        dialog.dismiss();

        if (firstButtonPressed) {
            firstCharCodeText.setText(valCurs.getValuteList().get(position).getCharCode());
            firstValuteValueText.setText("1");

        } else {
            secondCharCodeText.setText(valCurs.getValuteList().get(position).getCharCode());
            secondValuteValueText.setText("1");
        }
    }

    @Override
    public void showInputError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setValCurs(String valCurs) {
        if (firstValueEditedNow) {
            secondValuteValueText.setText(valCurs);
        } else {
            firstValuteValueText.setText(valCurs);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPreferences();
        dialog.dismiss();
    }
}
