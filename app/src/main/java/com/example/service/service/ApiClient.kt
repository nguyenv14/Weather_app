package com.example.service.service

import Weather
import WeatherData
import com.example.service.model.Location
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("direct")
    fun getListLocation(
        @Query("q") cityName: String,
        @Query("limit") limit : Int = 5,
        @Query("appId") appId: String = "141e92e2e782ed1f00c80abf9f8e0434"
    ) : Observable<List<Location>>

    @GET("current.json")
    fun getWeatherData(
        @Query("q") lat: String,
        @Query("key") appId: String = "0be69b11674d48d088b82642232707",
        @Query("aqi")  aqi: String = "yes"
    ) : Observable<WeatherData>

    @GET("forecast.json")
    fun getWeatherDataDetail(
        @Query("q") lat: String,
        @Query("days") days: Int = 5,
        @Query("key") appId: String = "0be69b11674d48d088b82642232707",
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alert: String = "no"
    ) : Observable<Weather>
}