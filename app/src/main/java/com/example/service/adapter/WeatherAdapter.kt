package com.example.service.adapter

import Forecast
import Forecastday
import Hour
import Weather
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.service.R
import java.text.DecimalFormat

class WeatherAdapter(val context: Context, val listWeather : List<Forecastday>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var date: TextView = itemView.findViewById(R.id.date);
        var temp: TextView = itemView.findViewById(R.id.temp);
        var templike: TextView = itemView.findViewById(R.id.templike);
        var status: TextView = itemView.findViewById(R.id.status)
        var statusImage: ImageView = itemView.findViewById(R.id.statusImage)
        var statusRain: TextView = itemView.findViewById(R.id.statusRain)
        var statusHumidity: TextView = itemView.findViewById(R.id.statusHumidity)
        var statusWind: TextView = itemView.findViewById(R.id.statusWind)
        var recyclerViewByHour: RecyclerView = itemView.findViewById(R.id.recylerviewByHour)
        val statusAir: TextView = itemView.findViewById(R.id.statusAir)
        val uvIndex: TextView = itemView.findViewById(R.id.uvIndex)
        val sunRise: TextView = itemView.findViewById(R.id.sunRise)
        val sunSet: TextView = itemView.findViewById(R.id.sunSet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false);
        return WeatherViewHolder(layout);
    }

    override fun getItemCount(): Int {
        if(listWeather.isNotEmpty()){
            return listWeather.size
        }
        return 0;
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        var dataCurrent = listWeather.get(position)
        val decimalFormat = DecimalFormat("#.00")
        holder.date.text = dataCurrent.date
        holder.temp.text = dataCurrent.day.maxtemp_c.toString()+ "°C"
        holder.templike.text =  dataCurrent.day.mintemp_c.toString() + "°C"
        holder.status.text = dataCurrent.day.condition.text
        Glide.with(context).load( "https:"+dataCurrent.day.condition.icon).into(holder.statusImage)
        holder.statusRain.text = dataCurrent.day.daily_chance_of_rain.toString() + "%"
        holder.statusHumidity.text = dataCurrent.day.avghumidity.toString() + "%"
        holder.statusWind.text = dataCurrent.day.maxwind_mph.toString() + "m/h"
        holder.recyclerViewByHour.layoutManager = LinearLayoutManager(context);
        holder.recyclerViewByHour.setHasFixedSize(true);
        val listHour : List<Hour> = dataCurrent.hour;
        val adapterHour = WeatherHourAdapter(context, listHour)
        holder.recyclerViewByHour.adapter = adapterHour;
        if(dataCurrent.day.air_quality.pm2_5 != null){
            holder.statusAir.text = decimalFormat.format(dataCurrent.day.air_quality.pm2_5).toString() + "μg/m3"
        }else{
            holder.statusAir.text = "0μg/m3"
        }
        holder.uvIndex.text = dataCurrent.day.uv.toString()
        holder.sunRise.text = dataCurrent.astro.sunrise
        holder.sunSet.text = dataCurrent.astro.sunset
    }


}