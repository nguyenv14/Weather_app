package com.example.service.adapter

import Hour
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.service.R
import com.example.service.service.Untils

class WeatherHourAdapter(val context: Context, val listHour: List<Hour>) :RecyclerView.Adapter<WeatherHourAdapter.WeatherHourViewHolder>() {

    class WeatherHourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hourDate: TextView = itemView.findViewById(R.id.hourDate)
        val statusImage: ImageView = itemView.findViewById(R.id.statusImageHour)
        val tempHour: TextView = itemView.findViewById(R.id.tempHour)
        val statusRainHour: TextView = itemView.findViewById(R.id.statusRainHour)
        val statusHumidityHour: TextView = itemView.findViewById(R.id.statusHumidityHour)
        val statusCloudHour: TextView = itemView.findViewById(R.id.statusCloudHour)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHourViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_by_hour, parent, false);
        return WeatherHourViewHolder(inflate);
    }

    override fun getItemCount(): Int {
        if(listHour.isNotEmpty()){
            return listHour.size
        }
        return 0;
    }

    override fun onBindViewHolder(holder: WeatherHourViewHolder, position: Int) {
        val dataCurrent = listHour.get(position);
        holder.hourDate.text = Untils.getTime(dataCurrent.time)
        Glide.with(context).load("https:" + dataCurrent.condition.icon).into(holder.statusImage)
        holder.tempHour.text = dataCurrent.temp_c.toString() + "°C"
        holder.statusRainHour.text = dataCurrent.chance_of_rain.toString() + "%"
        holder.statusHumidityHour.text = dataCurrent.humidity.toString() + "%"
        holder.statusCloudHour.text = dataCurrent.cloud.toString() + "%"
    }
}