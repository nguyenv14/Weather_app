package com.example.service.service

import java.text.DecimalFormat
import java.text.SimpleDateFormat

object Untils {
    const val BASE_URL = "http://api.openweathermap.org/geo/1.0/"
    const val BASE_WEATHER = "http://api.weatherapi.com/v1/"
//    fun getTwoDecimalPlaces(number: Double): Double {
//        val decimalFormat = DecimalFormat("#.00")
//        return decimalFormat.format(number).toString();
//    }

     final fun getTime(time : String) : String{
        val inputFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm");
        val outputFormat =  SimpleDateFormat("h:mm a");
        val date = inputFormat.parse(time);
        val outputDate = outputFormat.format(date);
        return outputDate;
    }
}