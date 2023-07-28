package com.example.service.presenter.main

import com.example.service.model.Location

interface MainInterFaceView {
    fun getListSuccess(locationList: ArrayList<Location>)
    fun getListError();
}