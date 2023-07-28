package com.example.service.presenter.home

import Weather
import WeatherData

interface HomeInterFacePresenter {
    fun getWeatherSuccess(weatherData: WeatherData);
    fun getWeatherError();

    fun getWeatherDetailSuccess(weather: Weather)
    fun getWeatherDetailError()
}