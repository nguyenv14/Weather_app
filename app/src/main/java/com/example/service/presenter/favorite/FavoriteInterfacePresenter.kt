package com.example.service.presenter.favorite

import WeatherData

interface FavoriteInterfacePresenter {
    fun getListSuccess(list: List<WeatherData>)
    fun getListError()
}