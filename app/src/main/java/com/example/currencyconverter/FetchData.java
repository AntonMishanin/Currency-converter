package com.example.currencyconverter;

public interface FetchData {

    void fetchData(ValCurs valCurs);

    void error(Throwable e);
}
