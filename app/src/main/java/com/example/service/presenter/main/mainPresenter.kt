package com.example.service.presenter.main

import android.content.Context
import android.widget.Toast
import com.example.service.model.Location
import com.example.service.service.ApiClient
import com.example.service.service.RetrofitClient
import com.example.service.service.Untils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(var context: Context, var mainInterFaceView: MainInterFaceView)
{
    var list = ArrayList<Location>()
    var composidable: CompositeDisposable = CompositeDisposable();
    var apiClient = RetrofitClient().getInstance(Untils.BASE_URL).create(ApiClient::class.java)
    fun getListLocation(nameCity: String){
        composidable.add(apiClient.getListLocation(nameCity).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if(it.isNotEmpty()){
                list.clear()
                list.addAll(it);
                mainInterFaceView.getListSuccess(list);
            }else{
                mainInterFaceView.getListError()
            }
        }){
            mainInterFaceView.getListError();
        })
    }
}