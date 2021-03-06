package com.example.currencyconverter.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class AppModule {

    private static final String BASE_URL = "https://www.cbr.ru/";

    @Singleton
    @Provides
    SimpleXmlConverterFactory provideSimpleXmlConverterFactory() {
        return SimpleXmlConverterFactory.create();
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                             SimpleXmlConverterFactory simpleXmlConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(simpleXmlConverterFactory)
                .build();
    }
}
