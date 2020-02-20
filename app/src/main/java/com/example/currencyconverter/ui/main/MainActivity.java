package com.example.currencyconverter.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.R;
import com.example.currencyconverter.network.model.MyValute;
import com.example.currencyconverter.network.model.ValCurs;

public class MainActivity extends AppCompatActivity implements OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {

            MyValute myRub = new MyValute();
            myRub.setCharCode("RUB");
            myRub.setName("Рубль");
            myRub.setNominal("1");
            myRub.setValue("1");

            valCurs = (ValCurs) arguments.getSerializable(MainActivity.class.getSimpleName());
            valCurs.getValuteList().add(0, myRub);
        }

        view = getLayoutInflater().inflate(R.layout.select_currency_layout, null);
        dialog = new Dialog(this, R.style.AppTheme);
        dialog.setContentView(view);

        firstValuteValueText = findViewById(R.id.valute_value_left);
        secondValuteValueText = findViewById(R.id.valute_value_right);
        firstCharCodeText = findViewById(R.id.valute_char_code_first);
        secondCharCodeText = findViewById(R.id.valute_char_code_second);

        firstCharCodeText.setText(valCurs.getValuteList().get(0).getCharCode());
        secondCharCodeText.setText(valCurs.getValuteList().get(1).getCharCode());



        firstValuteValueText.setText("1");

        firstValueEditedNow = true;
        textChanged(firstValuteValueText, firstCharCodeText, secondValuteValueText, secondCharCodeText);
        firstValueEditedNow = false;

        firstTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (secondValueEditedNow) {

                } else {
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
                if (firstValueEditedNow) {

                } else {
                    secondValueEditedNow = true;
                    textChanged(secondValuteValueText, secondCharCodeText, firstValuteValueText, firstCharCodeText);
                    secondValueEditedNow = false;
                }
            }
        };


        firstValuteValueText.addTextChangedListener(firstTextWatcher);
        secondValuteValueText.addTextChangedListener(secondTextWatcher);

        init();

    }

    void textChanged(EditText currentEditText, TextView currentCharCodeView, EditText secondEditText, TextView secondCharCodeView) {
        String currentValuteValue = currentEditText.getText().toString();
        float value = 0;
        try {
            value = Float.parseFloat(currentValuteValue);

        } catch (Exception e) {
            Toast.makeText(this, "Input error", Toast.LENGTH_SHORT).show();
        }
        if (value != 0) {

            String secondCharCode = secondCharCodeView.getText().toString();
            int positionSecondValute = 0;
            for (int i = 0; i < valCurs.getValuteList().size(); i++) {
                if (valCurs.getValuteList().get(i).getCharCode() == secondCharCode) {
                    positionSecondValute = i;
                }
            }

            String currentChatCode = currentCharCodeView.getText().toString();
            int positionCurrentValute = 0;
            for (int i = 0; i < valCurs.getValuteList().size(); i++) {
                if (valCurs.getValuteList().get(i).getCharCode() == currentChatCode) {
                    positionCurrentValute = i;
                }
            }

            float nominal2 = Float.parseFloat(valCurs.getValuteList().get(positionCurrentValute).getNominal());
            float value32 = Float.parseFloat(updateString(valCurs.getValuteList().get(positionCurrentValute).getValue()));

            float nominal = Float.parseFloat(valCurs.getValuteList().get(positionSecondValute).getNominal());
            float value3 = Float.parseFloat(updateString(valCurs.getValuteList().get(positionSecondValute).getValue()));
            float value2 = (nominal * value32) / (value3 * nominal2);

            secondEditText.setText(Float.toString(value2 * value));

        }

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

    @Override
    public void onItemClick(int position) {
        dialog.cancel();

      if(firstButtonPressed){
          firstCharCodeText.setText(valCurs.getValuteList().get(position).getCharCode());
          firstValuteValueText.setText("1");

      }else{
          secondCharCodeText.setText(valCurs.getValuteList().get(position).getCharCode());
          secondValuteValueText.setText("1");
      }
    }

    String updateString(String string) {

        String[] arrStrings1 = string.split(",");
        try {
            return arrStrings1[0] + "." + arrStrings1[1];
        } catch (Exception e) {
            return string;
        }
    }
}
