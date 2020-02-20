package com.example.currencyconverter.data.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "Valute")
public class MyValute implements Serializable {

    @Attribute(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private String numCode;

    @Element(name = "Nominal")
    private String nominal;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Name")
    private String name;

    @Element(name = "Value")
    private String value;

    private boolean chosenCurrency = false;

    public boolean getChosenCurrency() {
        return chosenCurrency;
    }

    public void setChosenCurrency(boolean chosenCurrency) {
        this.chosenCurrency = chosenCurrency;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
