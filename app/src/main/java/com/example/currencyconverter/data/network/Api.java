package com.example.currencyconverter.data.network;

import com.example.currencyconverter.data.model.ValCurs;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("scripts/XML_daily.asp")
    Single<ValCurs> getValCurs();
}
