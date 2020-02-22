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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.R;
import com.example.currencyconverter.data.model.ValCurs;
import com.example.currencyconverter.data.model.PrefModel;
import com.example.currencyconverter.di.component.DaggerMainActivityComponent;
import com.example.currencyconverter.di.module.MainActivityModule;
import com.example.currencyconverter.ui.main.adapter.AdapterMainActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements OnClickListener, MainView {

    private static final String DEFAULT_VALUTE_VALUE = "1";

    Dialog dialog;

    ValCurs valCurs;
    AdapterMainActivity adapterMainActivity;
    View view;

    EditText firstValuteValueText;
    EditText secondValuteValueText;

    TextView firstCharCodeText;
    TextView secondCharCodeText;

    boolean firstButtonPressed;

    TextWatcher firstTextWatcher;
    TextWatcher secondTextWatcher;
    boolean firstValueEditedNow = false;
    boolean secondValueEditedNow = false;

    @Inject
    MainPresenter mainPresenter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getValCursFromSplash();
        initToolbar();
        initDialog();
        initView();
        initTextWatcher();
        init();
        initDagger();
        getSharedPreferences();
    }

    void getValCursFromSplash() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            valCurs = (ValCurs) arguments.getSerializable(MainActivity.class.getSimpleName());
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
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
                    convertibleValute(firstValuteValueText, firstCharCodeText, secondCharCodeText);
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
                    convertibleValute(secondValuteValueText, secondCharCodeText, firstCharCodeText);
                    secondValueEditedNow = false;
                }
            }
        };
    }

    private void initDagger() {
        DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(sharedPreferences, valCurs, this))
                .build()
                .inject(this);
    }

    void convertibleValute(EditText currentEditText, TextView currentCharCodeView, TextView secondCharCodeView) {
        String currentEditableValuteValue = currentEditText.getText().toString();
        String secondCharCode = secondCharCodeView.getText().toString();
        String currentChatCode = currentCharCodeView.getText().toString();

        mainPresenter.convertibleValute(currentEditableValuteValue, currentChatCode, secondCharCode);


    }

    private void init() {
        adapterMainActivity = new AdapterMainActivity(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMainActivity);

        firstValuteValueText.addTextChangedListener(firstTextWatcher);
        secondValuteValueText.addTextChangedListener(secondTextWatcher);

        sharedPreferences = getPreferences(MODE_PRIVATE);
    }

    public void onClickChangeFirstVal(View view) {
        firstButtonPressed = true;

        String firstCharCode = firstCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, firstCharCode);
        dialog.show();
    }

    public void onClickChangeSecondVal(View view) {
        firstButtonPressed = false;

        String secondCharCode = secondCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, secondCharCode);
        dialog.show();
    }

    private void setSharedPreferences() {
        PrefModel prefModel = new PrefModel(
                firstCharCodeText.getText().toString(),
                secondCharCodeText.getText().toString(),
                firstValuteValueText.getText().toString(),
                dialog.isShowing(),
                firstButtonPressed);

        mainPresenter.setSharedPreferences(prefModel);
    }

    private void getSharedPreferences() {
        PrefModel prefModel = mainPresenter.getSharedPreferences();
        firstCharCodeText.setText(prefModel.getFirstCharCodeText());
        secondCharCodeText.setText(prefModel.getSecondCharCodeText());
        firstValuteValueText.setText(prefModel.getFirstValuteValueText());
        firstButtonPressed = prefModel.isFirstButtonPressed();

        secondValueEditedNow = true;
        convertibleValute(secondValuteValueText, secondCharCodeText, firstCharCodeText);
        secondValueEditedNow = false;

        if (prefModel.isDialogIsShowing()) {
            if (firstButtonPressed) {
                String firstCharCode = firstCharCodeText.getText().toString();
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
            firstValuteValueText.setText(DEFAULT_VALUTE_VALUE);

        } else {
            secondCharCodeText.setText(valCurs.getValuteList().get(position).getCharCode());
            secondValuteValueText.setText(DEFAULT_VALUTE_VALUE);
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

    public void onButtonSwapValute(View view) {
        String firstCharCode = firstCharCodeText.getText().toString();
        String secondCharCode = secondCharCodeText.getText().toString();
        String firstValuteValue = firstValuteValueText.getText().toString();

        firstCharCodeText.setText(secondCharCode);
        secondCharCodeText.setText(firstCharCode);
        secondValuteValueText.setText(firstValuteValue);
    }

    public void onClickToolbarCancel(View view) {
        dialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setSharedPreferences();
        dialog.dismiss();
    }
}
