package com.example.service

import Weather
import WeatherData
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.service.presenter.home.HomeInterFacePresenter
import com.example.service.presenter.home.HomePresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat


class HomeActivity : AppCompatActivity(), HomeInterFacePresenter {
     var  REQUEST_LOCATION_PERMISSION = 1
    lateinit var locationManager: LocationManager
    lateinit var viewSearch: LinearLayout
    lateinit var nameCity: TextView
    lateinit var statusImage: ImageView
    lateinit var temp: TextView
    lateinit var tempLike: TextView
    lateinit var status: TextView
    lateinit var statusRain: TextView
    lateinit var statusAir: TextView
    lateinit var statusHumidity: TextView
    lateinit var statusWind: TextView
    lateinit var viewCurrentLocation: LinearLayout
    lateinit var isVisible: LinearLayout
    lateinit var homePresenter: HomePresenter
    lateinit var dialog: AlertDialog
    lateinit var gotoFavorite: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUI()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        homePresenter = HomePresenter(this, this)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1f, object : LocationListener{
                override fun onLocationChanged(p0: Location) {
//                        Toast.makeText(this@HomeActivity, p0.latitude.toString() + "" + p0.longitude, Toast.LENGTH_SHORT).show()
                    homePresenter.getWeatherDataLocation(p0.latitude.toString() + "," + p0.longitude.toString())
                }
            })
        }
    }

    private fun initUI() {
        nameCity = findViewById(R.id.nameCity)
        temp = findViewById(R.id.temp)
        tempLike = findViewById(R.id.templike)
        statusImage = findViewById(R.id.statusImage)
        status = findViewById(R.id.status)
        statusRain = findViewById(R.id.statusRain)
        statusAir = findViewById(R.id.statusAir)
        statusHumidity = findViewById(R.id.statusHumidity)
        statusWind = findViewById(R.id.statusWind)
        viewCurrentLocation = findViewById(R.id.viewCurrentLocation)
        isVisible = findViewById(R.id.isVisible)
        viewSearch = findViewById(R.id.viewSearch)
        gotoFavorite = findViewById(R.id.gotoFavorite)
        viewSearch.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        gotoFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getWeatherSuccess(weatherData: WeatherData) {
        val decimalFormat = DecimalFormat("#.00")
        nameCity.text = "Your city: " + weatherData.location.name + ", "+ weatherData.location.country
        temp.text = weatherData.current.temp_c.toString() + "°C"
        tempLike.text = weatherData.current.feelslike_c.toString() + "°C"
        Glide.with(this@HomeActivity).load("https:" + weatherData.current.condition.icon).into(statusImage)
        status.text = weatherData.current.condition.text
        statusRain.text = weatherData.current.cloud.toString() + "%"
        statusAir.text = decimalFormat.format(weatherData.current.air_quality.pm2_5).toString() + "μg/m3"
        statusHumidity.text = weatherData.current.humidity.toString() + "%"
        statusWind.text = weatherData.current.wind_mph.toString() + "m/h"
        isVisible.visibility = View.GONE
        viewCurrentLocation.visibility = View.VISIBLE
        viewCurrentLocation.setOnClickListener {
            showProgressDialog(this@HomeActivity)
            if(dialog.isShowing){
                homePresenter.goToLocationWeather(weatherData.location.name);
            }
        }
    }

    override fun getWeatherError() {
        isVisible.visibility = View.VISIBLE
        viewCurrentLocation.visibility = View.GONE
    }

    override fun getWeatherDetailSuccess(weather: Weather) {
        if(dialog.isShowing){
            dialog.dismiss()
        }
        val intent = Intent(this@HomeActivity, Location_Weather::class.java);
        intent.putExtra("weather", weather)
        startActivity(intent)
    }

    override fun getWeatherDetailError() {
        if(dialog.isShowing){
            dialog.dismiss()
        }
        Toast.makeText(this@HomeActivity, "Have an error in app!", Toast.LENGTH_SHORT).show()
    }

    private fun showProgressDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
        dialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                // Khi người dùng nhấn nút back, đóng dialog
                dialog?.dismiss()
                true
            } else {
                false
            }
        }
    }
}