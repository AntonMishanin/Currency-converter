package com.example.currencyconverter;

import android.util.Xml;

import io.reactivex.Single;
import okhttp3.Response;
import retrofit2.http.GET;

public interface Api {

    @GET("scripts/XML_daily.asp")
    Single<ValCurs> getValCurs();

}
