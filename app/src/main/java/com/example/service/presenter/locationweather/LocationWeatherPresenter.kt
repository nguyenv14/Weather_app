package com.example.service.presenter.locationweather

import android.content.Context
import com.example.service.model.Favorite
import com.example.service.room.favorite.FavoriteDatabase

class LocationWeatherPresenter(val context: Context, val locationWeatherInterfacePresenter: LocationWeatherInterfacePresenter) {
    fun getDataFavorite(nameCity: String){
        val favorite: Favorite = FavoriteDatabase.getInstance(context)!!.favoriteDAO()!!.getFavoriteName(nameCity)
        if(favorite != null){
            locationWeatherInterfacePresenter.getDataFavoriteSuccess()
        }else{
            locationWeatherInterfacePresenter.getDataFavoriteError()
        }
    }

    fun deleteFavorite(nameCity: String){
        val favorite: Favorite = FavoriteDatabase.getInstance(context)!!.favoriteDAO()!!.getFavoriteName(nameCity);
        FavoriteDatabase.getInstance(context)!!.favoriteDAO()!!.deleteFavorite(favorite)
        locationWeatherInterfacePresenter.getSuccessDeleteFavorite()
    }

    fun saveFavorite(nameCity: String){
        val favorite: Favorite = Favorite(favorite_city = nameCity)
        FavoriteDatabase.getInstance(context)!!.favoriteDAO()!!.insertFavorite(favorite)
        locationWeatherInterfacePresenter.getSuccessSaveFavorite()
    }
}