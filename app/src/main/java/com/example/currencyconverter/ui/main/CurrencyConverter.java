package com.example.currencyconverter.ui.main;

import android.annotation.SuppressLint;

import com.example.currencyconverter.data.model.ValCurs;

public class CurrencyConverter {

    private static final String INPUT_ERROR_MESSAGE = "Input error";
    private static final String DOT = ".";
    private static final String COMMA = ",";
    private static final String SMALL_VALUE = "0.00";

    private ValCurs valCurs;

    public CurrencyConverter(ValCurs valCurs) {
        this.valCurs = valCurs;
    }

    @SuppressLint("DefaultLocale")
    void convertibleValute(String currentEditableValuteValue, String currentEditableCharCode, String secondCharCode, OnValCursListener onValCursListener) {
        float valueFromCurrentEditableText;

        try {
            valueFromCurrentEditableText = Float.parseFloat(replacingCommaWithDot(currentEditableValuteValue));
        } catch (Exception e) {
            onValCursListener.onInputError(INPUT_ERROR_MESSAGE);
            return;
        }

        int positionSecondValute = findPositionByCharCode(secondCharCode);
        int positionCurrentEditableValute = findPositionByCharCode(currentEditableCharCode);

        float nominalCurrentEditableValute = Float.parseFloat(valCurs.getValuteList().get(positionCurrentEditableValute).getNominal());
        float valueCurrentEditableValute = Float.parseFloat(replacingCommaWithDot(valCurs.getValuteList().get(positionCurrentEditableValute).getValue()));
        float nominalSecondValute = Float.parseFloat(valCurs.getValuteList().get(positionSecondValute).getNominal());
        float valueSecondValute = Float.parseFloat(replacingCommaWithDot(valCurs.getValuteList().get(positionSecondValute).getValue()));

        float proportionValute = (nominalSecondValute * valueCurrentEditableValute) / (valueSecondValute * nominalCurrentEditableValute);
        float valueForSecondValuteText = valueFromCurrentEditableText * proportionValute;

        //If the valueForSecondValuteText is too small, the result will be 0.00, because we leave 3 decimal places
        if ((String.format("%.2f", valueForSecondValuteText)).equals(SMALL_VALUE)) {
            onValCursListener.onSuccess(String.format("%.3f", valueForSecondValuteText));
        } else {
            onValCursListener.onSuccess(String.format("%.2f", valueForSecondValuteText));
        }
    }

    //Because the float type contains a dot, not a comma
    private String replacingCommaWithDot(String string) {
        try {
            String[] arrStrings1 = string.split(COMMA);
            return arrStrings1[0] + DOT + arrStrings1[1];
        } catch (Exception e) {
            return string;
        }
    }

    private int findPositionByCharCode(String charCode) {
        int position = 0;
        for (int i = 0; i < valCurs.getValuteList().size(); i++) {
            if (valCurs.getValuteList().get(i).getCharCode().equals(charCode)) {
                position = i;
            }
        }
        return position;
    }
}
