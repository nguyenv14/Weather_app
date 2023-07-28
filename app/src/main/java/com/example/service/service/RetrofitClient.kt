package com.example.service.service

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private var instance: Retrofit? = null;

    fun getInstance(baseUrl: String): Retrofit {
        if(instance == null){
            instance = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        }
        return instance!!;
    }
}