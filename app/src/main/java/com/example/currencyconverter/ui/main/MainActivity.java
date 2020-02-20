package com.example.currencyconverter.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        getValCursFromSplash();
        dialogInit();
        initView();
        initTextWatcher();

        currencyConverter = new CurrencyConverter(valCurs);
        mainPresenter = new MainPresenter(currencyConverter, this);

        firstCharCodeText.setText(valCurs.getValuteList().get(0).getCharCode());
        secondCharCodeText.setText(valCurs.getValuteList().get(1).getCharCode());
        firstValuteValueText.setText("1");

        firstValueEditedNow = true;
        textChanged(firstValuteValueText, firstCharCodeText, secondValuteValueText, secondCharCodeText);
        firstValueEditedNow = false;

        firstValuteValueText.addTextChangedListener(firstTextWatcher);
        secondValuteValueText.addTextChangedListener(secondTextWatcher);

        init();

    }

    void getValCursFromSplash() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
/*
            MyValute myRub = new MyValute();
            myRub.setCharCode("RUB");
            myRub.setName("Рубль");
            myRub.setNominal("1");
            myRub.setValue("1");
*/
            valCurs = (ValCurs) arguments.getSerializable(MainActivity.class.getSimpleName());
            // valCurs.getValuteList().add(0, myRub);
        }
    }

    void dialogInit() {
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

        String value = mainPresenter.startConverter(currentValuteValue, currentChatCode, secondCharCode);
        secondEditText.setText(value);

    }

    private void init() {
        adapterMainActivity = new AdapterMainActivity(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMainActivity);
    }

    public void onChangeFirstVal(View view) {
        firstButtonPressed = true;

        String firstCharCode = firstCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, firstCharCode);
        dialog.show();
    }

    public void onChangeSecondVal(View view) {
        firstButtonPressed = false;

        String secondCharCode = secondCharCodeText.getText().toString();
        adapterMainActivity.setValute(valCurs, secondCharCode);
        dialog.show();
    }

    private void saveSharedPreferences() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("q", );
        editor.commit();
    }

    private void loadSharedPreferences() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferences.getString();
    }

    @Override
    public void onItemClick(int position) {
        dialog.cancel();

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
