package com.example.currencyconverter.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "ValCurs")
public class ValCurs implements Serializable {

    @Attribute(name = "Date")
    private String data;

    @Attribute(name = "name")
    private String name;

    private MyValute currentValute;

    @ElementList(name = "Valute", inline = true)
    private List<MyValute> valuteList;

    public List<MyValute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<MyValute> valuteList) {
        this.valuteList = valuteList;
    }
}
