package com.example.service.presenter.favorite

import WeatherData
import android.content.Context
import android.widget.Toast
import com.example.service.model.Favorite
import com.example.service.service.ApiClient
import com.example.service.service.RetrofitClient
import com.example.service.service.Untils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritePresenter(val context: Context, val favoriteInterfacePresenter: FavoriteInterfacePresenter) {
    var composidable: CompositeDisposable = CompositeDisposable();
    var apiClient = RetrofitClient().getInstance(Untils.BASE_WEATHER).create(ApiClient::class.java)
    var listWeatherData = ArrayList<WeatherData>();
    fun getDataListWeatherData(listFavorite: List<Favorite>){
        val observables = ArrayList<Observable<WeatherData>>()
        for(item in listFavorite){
//            composidable.add(apiClient.getWeatherData(item.favorite_city!!).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe({
//                    listWeatherData.add(it)
//                }){
//                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show();
//                })
//        }
            val observable = apiClient.getWeatherData(item.favorite_city!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    listWeatherData.add(it)
                }
                .doOnError {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            observables.add(observable)
//        if(listWeatherData.size.toString() > "0"){
//                Toast.makeText(context, "34", Toast.LENGTH_SHORT).show()
//                favoriteInterfacePresenter.getListSuccess(listWeatherData);
//            }else{
//                Toast.makeText(context, "32", Toast.LENGTH_SHORT).show()
//                favoriteInterfacePresenter.getListError()
//            }
        }
        Observable.zip(observables) {
            // Thực hiện khi tất cả các API calls đã hoàn tất
            // Cập nhật UI hoặc gọi các phương thức khác tại đây
            if (listWeatherData.isNotEmpty()) {
                favoriteInterfacePresenter.getListSuccess(listWeatherData)
            } else {
                favoriteInterfacePresenter.getListError()
            }
        }.subscribe()
    }
}