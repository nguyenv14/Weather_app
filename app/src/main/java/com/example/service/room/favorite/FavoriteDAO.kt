package com.example.service.room.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.service.model.Favorite

@Dao
interface FavoriteDAO {
    @Insert
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): List<Favorite>

    @Query("SELECT * FROM favorite where favorite_city = :favorite_name")
    fun getFavoriteName(favorite_name: String): Favorite

    @Delete
    fun deleteFavorite(favorite: Favorite)
}