package com.example.service.presenter.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.service.Location_Weather
import com.example.service.service.ApiClient
import com.example.service.service.RetrofitClient
import com.example.service.service.Untils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(val context: Context, val homeInterFacePresenter: HomeInterFacePresenter) {
    var composidable: CompositeDisposable = CompositeDisposable();
    var apiClient = RetrofitClient().getInstance(Untils.BASE_WEATHER).create(ApiClient::class.java)

    fun getWeatherDataLocation(location: String){
        composidable.add(apiClient.getWeatherData(location).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.location.lat != null){
                    homeInterFacePresenter.getWeatherSuccess(it);
                }else{
                    homeInterFacePresenter.getWeatherError();
                }
            }){
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show();
            })
    }

    fun goToLocationWeather(location: String){
        composidable.add(apiClient.getWeatherDataDetail(location)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                homeInterFacePresenter.getWeatherDetailSuccess(it)
            }){
                Log.i("Nguyên", it.message.toString())
                homeInterFacePresenter.getWeatherDetailError()
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            })
    }
}