package com.example.service.adapter

import WeatherData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.service.R
import java.text.DecimalFormat

class WeatherFavoriteAdapter(val context: Context, val listData: List<WeatherData>): RecyclerView.Adapter<WeatherFavoriteAdapter.FavoriteViewHolder>(){

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var nameCity: TextView = itemView.findViewById(R.id.nameCity)
         var statusImage: ImageView = itemView.findViewById(R.id.statusImage)
         var temp: TextView = itemView.findViewById(R.id.temp)
         var tempLike: TextView = itemView.findViewById(R.id.templike)
         var status: TextView = itemView.findViewById(R.id.status)
         var statusRain: TextView = itemView.findViewById(R.id.statusRain)
         var statusAir: TextView = itemView.findViewById(R.id.statusAir)
         var statusHumidity: TextView = itemView.findViewById(R.id.statusHumidity)
         var statusWind: TextView = itemView.findViewById(R.id.statusWind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val viewInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_favorite, parent, false)
        return FavoriteViewHolder(viewInflater)
    }

    override fun getItemCount(): Int {
        if(listData.isNotEmpty()){
            return listData.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val dataCurrent = listData.get(position)
        val decimalFormat = DecimalFormat("#.00")
        holder.nameCity.text = dataCurrent.location.name + ", " + dataCurrent.location.country;
        holder.temp.text = dataCurrent.current.temp_c.toString() + "°C"
        holder.tempLike.text = dataCurrent.current.feelslike_c.toString() + "°C"
        Glide.with(context).load("https:" + dataCurrent.current.condition.icon).into(holder.statusImage)
        holder.status.text = dataCurrent.current.condition.text
        holder.statusRain.text = dataCurrent.current.cloud.toString() + "%"
        holder.statusAir.text = decimalFormat.format(dataCurrent.current.air_quality.pm2_5).toString() + "μg/m3"
        holder.statusHumidity.text = dataCurrent.current.humidity.toString() + "%"
        holder.statusWind.text = dataCurrent.current.wind_mph.toString() + "m/h"
    }
}