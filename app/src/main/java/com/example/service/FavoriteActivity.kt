package com.example.service

import WeatherData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service.adapter.WeatherFavoriteAdapter
import com.example.service.model.Favorite
import com.example.service.presenter.favorite.FavoriteInterfacePresenter
import com.example.service.presenter.favorite.FavoritePresenter
import com.example.service.room.favorite.FavoriteDatabase

class FavoriteActivity : AppCompatActivity(), FavoriteInterfacePresenter {
    lateinit var isEmpty: ImageView;
    lateinit var recyclerView: RecyclerView
    lateinit var isLoad: LinearLayout
    lateinit var listFavorite: List<Favorite>
    lateinit var favoritePresenter: FavoritePresenter
    lateinit var favoriteAdapter: WeatherFavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favoritePresenter = FavoritePresenter(this, this)
        initUI()
        listFavorite = FavoriteDatabase.getInstance(this)?.favoriteDAO()?.getAllFavorite()!!
        if(listFavorite.isEmpty()){
            isEmpty.visibility = View.VISIBLE
            isLoad.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }else{
            Toast.makeText(this@FavoriteActivity, listFavorite.size.toString(), Toast.LENGTH_SHORT).show()
            isEmpty.visibility = View.GONE
            isLoad.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            favoritePresenter.getDataListWeatherData(listFavorite)
        }
    }

    private fun initUI() {
        isEmpty = findViewById(R.id.isEmpty)
        isLoad = findViewById(R.id.isVisible)
        recyclerView = findViewById(R.id.recycviewLocation)
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true)
    }

    override fun getListSuccess(list: List<WeatherData>) {
        Toast.makeText(this@FavoriteActivity, list.size.toString(), Toast.LENGTH_SHORT).show()

        favoriteAdapter = WeatherFavoriteAdapter(this@FavoriteActivity, list)
        recyclerView.adapter = favoriteAdapter
        isLoad.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun getListError() {
        isEmpty.visibility = View.GONE
        isLoad.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}