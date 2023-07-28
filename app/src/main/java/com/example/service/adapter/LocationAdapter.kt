package com.example.service.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.service.Location_Weather
import com.example.service.R
import com.example.service.model.Location
import com.example.service.service.ApiClient
import com.example.service.service.RetrofitClient
import com.example.service.service.Untils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class LocationAdapter(val context: Context, val listLocation: ArrayList<Location>): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    var compositeDisposable = CompositeDisposable();
    var apiClient = RetrofitClient().getInstance(Untils.BASE_URL).create(ApiClient::class.java)
    var apiClientWeather = RetrofitClient().getInstance(Untils.BASE_WEATHER).create(ApiClient::class.java);
    class LocationViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew){
        val cityName: TextView = itemVIew.findViewById(R.id.nameCity);
        val statusImage: ImageView = itemVIew.findViewById(R.id.statusImage)
        val temp: TextView = itemVIew.findViewById(R.id.temp);
        val status: TextView = itemVIew.findViewById(R.id.status)
        val statusRain: TextView = itemVIew.findViewById(R.id.statusRain)
        val statusHumidity: TextView = itemVIew.findViewById(R.id.statusHumidity)
        val statusWind: TextView = itemVIew.findViewById(R.id.statusWind)
        val statusAir: TextView = itemVIew.findViewById(R.id.statusAir)
        val indicator: ProgressBar = itemVIew.findViewById(R.id.indicator)
        val linear: LinearLayout = itemVIew.findViewById(R.id.linear)
        var cardView : CardView = itemVIew.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val viewLayout = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return LocationViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        if(listLocation.isNotEmpty()){
            return listLocation.size;
        }
        return 0;
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val dataCurrent = listLocation.get(position);
        val decimalFormat = DecimalFormat("#.00")
        holder.cityName.text = dataCurrent.name+ ", " + dataCurrent.country;
        compositeDisposable.add(apiClientWeather.getWeatherData(dataCurrent.lat!!.toString() + "," + dataCurrent.lon!!.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                holder.temp.text = it.current.temp_c.toString() + "°C";
                holder.status.text = it.current.condition.text
                Glide.with(context).load( "https:"+it.current.condition.icon).into(holder.statusImage)
                holder.statusHumidity.text = it.current.humidity.toString() + "%";
                holder.statusWind.text = it.current.wind_mph.toString() + "m/h"
                holder.statusRain.text = it.current.cloud.toString() + "%"
                holder.statusAir.text = decimalFormat.format(it.current.air_quality.pm2_5).toString() + "μg/m3"
                holder.indicator.visibility = View.GONE;
                holder.linear.visibility = View.VISIBLE
        }){
                Log.i("Nguyên", it.message.toString());
            Toast.makeText(context, it.message + "1", Toast.LENGTH_SHORT).show()
        })
        holder.cardView.setOnClickListener {
            compositeDisposable.add(apiClientWeather.getWeatherDataDetail(dataCurrent.lat!!.toString() + "," + dataCurrent.lon!!.toString())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    val intent = Intent(context, Location_Weather::class.java);
                    intent.putExtra("weather", it)
                    context.startActivity(intent)
                }){
                    Log.i("Nguyên", it.message.toString())
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                })
        }
    }
}