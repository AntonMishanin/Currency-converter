package com.example.currencyconverter.network;

import com.example.currencyconverter.network.model.ValCurs;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("scripts/XML_daily.asp")
    Single<ValCurs> getValCurs();

}
