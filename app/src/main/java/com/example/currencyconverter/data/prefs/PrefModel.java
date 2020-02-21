package com.example.currencyconverter.data.prefs;

public class PrefModel {

    private String firstCharCodeText;
    private String secondCharCodeText;
    private String firstValuteValueText;
    private boolean dialogIsShowing;
    private boolean firstButtonPressed;

    public PrefModel(
            String firstCharCodeText,
            String secondCharCodeText,
            String firstValuteValueText,
            boolean dialogIsShowing,
            boolean firstButtonPressed) {

        this.firstCharCodeText = firstCharCodeText;
        this.secondCharCodeText = secondCharCodeText;
        this.firstValuteValueText = firstValuteValueText;
        this.dialogIsShowing = dialogIsShowing;
        this.firstButtonPressed = firstButtonPressed;
    }

    public String getFirstCharCodeText() {
        return firstCharCodeText;
    }


    public String getSecondCharCodeText() {
        return secondCharCodeText;
    }


    public String getFirstValuteValueText() {
        return firstValuteValueText;
    }


    public boolean isDialogIsShowing() {
        return dialogIsShowing;
    }

    public boolean isFirstButtonPressed() {
        return firstButtonPressed;
    }
}
