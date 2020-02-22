package com.example.currencyconverter.data.prefs;

import android.content.SharedPreferences;

import com.example.currencyconverter.data.model.PrefModel;

public class PreferencesHelper {

    private static final String KEY_FIRST_CHAR_CODE = "KEY_FIRST_CHAR_CODE";
    private static final String KEY_SECOND_CHAR_CODE = "KEY_SECOND_CHAR_CODE";
    private static final String KEY_FIRST_VALUTE_VALUE = "KEY_FIRST_VALUTE_VALUE";
    private static final String KEY_DIALOG_IS_SHOWING = "KEY_DIALOG_IS_SHOWING";
    private static final String KEY_FIRST_BUTTON_IS_PRESSED = "KEY_FIRST_BUTTON_IS_PRESSED";

    private static final String DEF_VALUE_FIRST_CHAR_CODE = "USD";
    private static final String DEF_VALUE_SECOND_CHAR_CODE = "EUR";
    private static final String DEF_VALUE_FIRST_VALUTE_VALUE = "1";
    private static final boolean DEF_VALUE_DIALOG_IS_SHOWING = false;
    private static final boolean DEF_VALUE_FIRST_BUTTON_IS_PRESSED = true;

    private SharedPreferences sharedPreferences;

    public PreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setSharedPreferences(PrefModel prefModel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_CHAR_CODE, prefModel.getFirstCharCodeText());
        editor.putString(KEY_SECOND_CHAR_CODE, prefModel.getSecondCharCodeText());
        editor.putString(KEY_FIRST_VALUTE_VALUE, prefModel.getFirstValuteValueText());
        editor.putBoolean(KEY_DIALOG_IS_SHOWING, prefModel.isDialogIsShowing());
        editor.putBoolean(KEY_FIRST_BUTTON_IS_PRESSED, prefModel.isFirstButtonPressed());
        editor.apply();
    }

    public PrefModel getSharedPreferences() {
        return new PrefModel(
                sharedPreferences.getString(KEY_FIRST_CHAR_CODE, DEF_VALUE_FIRST_CHAR_CODE),
                sharedPreferences.getString(KEY_SECOND_CHAR_CODE, DEF_VALUE_SECOND_CHAR_CODE),
                sharedPreferences.getString(KEY_FIRST_VALUTE_VALUE, DEF_VALUE_FIRST_VALUTE_VALUE),
                sharedPreferences.getBoolean(KEY_DIALOG_IS_SHOWING, DEF_VALUE_DIALOG_IS_SHOWING),
                sharedPreferences.getBoolean(KEY_FIRST_BUTTON_IS_PRESSED, DEF_VALUE_FIRST_BUTTON_IS_PRESSED)
        );
    }
}
