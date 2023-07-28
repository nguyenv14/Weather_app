package com.example.service

import Weather
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.service.adapter.WeatherAdapter
import com.example.service.model.Favorite
import com.example.service.presenter.locationweather.LocationWeatherInterfacePresenter
import com.example.service.presenter.locationweather.LocationWeatherPresenter
import com.example.service.room.favorite.FavoriteDatabase
import com.google.android.material.tabs.TabLayout


class Location_Weather : AppCompatActivity(), LocationWeatherInterfacePresenter {
    lateinit var btnBack: ImageView;
    lateinit var recyclerWeatherDetail: RecyclerView
    lateinit var nameCity: TextView
    lateinit var weather: Weather
    lateinit var weatherAdapter: WeatherAdapter
    lateinit var favoriteNo: ImageView
    lateinit var favoriteYes: ImageView
    lateinit var locationWeatherPresenter: LocationWeatherPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_weather)
        locationWeatherPresenter = LocationWeatherPresenter(this, this)
        weather = intent.getSerializableExtra("weather") as Weather
        initUI();
        locationWeatherPresenter.getDataFavorite(weather.location.name);
        btnBack.setOnClickListener {
            finish()
        }
        favoriteNo.setOnClickListener {
            locationWeatherPresenter.saveFavorite(weather.location.name)
            Toast.makeText(this, "Added the list favorite!", Toast.LENGTH_SHORT).show()
        }
        favoriteYes.setOnClickListener {
            locationWeatherPresenter.deleteFavorite(weather.location.name)
            Toast.makeText(this, "Deleted the list favorite!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun initUI() {
        btnBack = findViewById(R.id.btnBack);
        recyclerWeatherDetail = findViewById(R.id.recylerWeatherDetail)
        nameCity = findViewById(R.id.nameCity)
        nameCity.text = weather.location.name + "," + weather.location.country;
        recyclerWeatherDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        favoriteNo = findViewById(R.id.favoriteNo)
        favoriteYes = findViewById(R.id.favoriteYes)
        weatherAdapter = WeatherAdapter(this, weather.forecast.forecastday)
        recyclerWeatherDetail.adapter = weatherAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerWeatherDetail)
    }

    override fun getDataFavoriteSuccess() {
        favoriteYes.visibility = View.VISIBLE
        favoriteNo.visibility = View.GONE
    }

    override fun getDataFavoriteError() {
        favoriteYes.visibility = View.GONE
        favoriteNo.visibility = View.VISIBLE
    }

    override fun getSuccessSaveFavorite() {
        favoriteYes.visibility = View.VISIBLE
        favoriteNo.visibility = View.GONE
    }

    override fun getSuccessDeleteFavorite() {
        favoriteYes.visibility = View.GONE
        favoriteNo.visibility = View.VISIBLE
    }
}