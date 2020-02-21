package com.example.currencyconverter.ui.main;


import com.example.currencyconverter.data.model.ValCurs;

public class CurrencyConverter {

    private ValCurs valCurs;

    public CurrencyConverter(ValCurs valCurs) {
        this.valCurs = valCurs;
    }

    void textChanged(String currentValuteValue, String currentChatCode, String secondCharCode, OnValCursListener onValCursListener) {
        float value = 0;
        float value2;
        try {
            value = Float.parseFloat(updateString(currentValuteValue));

        } catch (Exception e) {
            onValCursListener.onInputError("Input error");
        }
        if (value != 0) {

            int positionSecondValute = 0;
            for (int i = 0; i < valCurs.getValuteList().size(); i++) {
                if (valCurs.getValuteList().get(i).getCharCode().equals(secondCharCode)) {
                    positionSecondValute = i;
                }
            }


            int positionCurrentValute = 0;
            for (int i = 0; i < valCurs.getValuteList().size(); i++) {
                if (valCurs.getValuteList().get(i).getCharCode().equals(currentChatCode)) {
                    positionCurrentValute = i;
                }
            }

            float nominal2 = Float.parseFloat(valCurs.getValuteList().get(positionCurrentValute).getNominal());
            float value32 = Float.parseFloat(updateString(valCurs.getValuteList().get(positionCurrentValute).getValue()));

            float nominal = Float.parseFloat(valCurs.getValuteList().get(positionSecondValute).getNominal());
            float value3 = Float.parseFloat(updateString(valCurs.getValuteList().get(positionSecondValute).getValue()));
            value2 = (nominal * value32) / (value3 * nominal2);

            if((String.format("%.2f", value * value2)).equals("0,00")) {
                onValCursListener.onSuccess(String.format("%.3f", value * value2));
            }else{
                onValCursListener.onSuccess(String.format("%.2f", value * value2));
            }
        }


    }

    private String updateString(String string) {
        try {
            String[] arrStrings1 = string.split(",");
            return arrStrings1[0] + "." + arrStrings1[1];
        } catch (Exception e) {
            return string;
        }
    }
}
