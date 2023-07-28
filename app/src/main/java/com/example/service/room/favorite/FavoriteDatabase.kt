package com.example.service.room.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.service.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDAO(): FavoriteDAO?
        companion object {
            private var INSTANCE: FavoriteDatabase? = null
            fun getInstance(context: Context): FavoriteDatabase? {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "favorite.db").allowMainThreadQueries().build()
                }
                return INSTANCE
            }
        }

}