package com.example.service


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service.adapter.LocationAdapter
import com.example.service.model.Location
import com.example.service.presenter.main.MainInterFaceView
import com.example.service.presenter.main.MainPresenter
import com.example.service.service.ApiClient
import com.example.service.service.RetrofitClient
import com.example.service.service.Untils
import com.google.android.material.button.MaterialButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MainInterFaceView {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var mainPresenter: MainPresenter;
    lateinit var searchView : SearchView;
    lateinit var btnGetLocation: MaterialButton
    lateinit var locationRecyclerView: RecyclerView
    lateinit var isEmpty: ImageView
    lateinit var locationAdapter: LocationAdapter
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi();
        mainPresenter = MainPresenter(this, this);
        apiClient = RetrofitClient().getInstance(Untils.BASE_URL).create(ApiClient::class.java)
//        compositeDisposable.add(apiClient.getListLocation("Tam Kỳ").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
//            Toast.makeText(this, it[0].name, Toast.LENGTH_LONG).show()
//            }){
//            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//            Log.i("Nguyên", it.message.toString());
//        })
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
               mainPresenter.getListLocation(newText)
                return true
            }
        })
    }

    private fun initUi() {
        searchView = findViewById(R.id.searchView)
        btnGetLocation = findViewById(R.id.btnGetLocation)
        locationRecyclerView = findViewById(R.id.locationRecylerview)
        isEmpty = findViewById(R.id.isEmpty)
        locationRecyclerView.layoutManager = LinearLayoutManager(this);
        locationRecyclerView.setHasFixedSize(true);
    }

//    @SuppressLint("MissingPermission")
//    private fun sendOnNotification() {
//        var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);
//
//        var notification = NotificationCompat.Builder(this, MyApplication.MY_CHANNEL_ID).setContentTitle("This push notification")
//            .setContentText("Message push notification").setSmallIcon(R.drawable.ic_launcher_background)
//            .setLargeIcon(bitmap)
//            .setColor(resources.getColor(androidx.appcompat.R.color.abc_background_cache_hint_selector_material_dark))
//            .build();
//        var notificationCompat= NotificationManagerCompat.from(this)
//        notificationCompat.notify(getNotificationId(), notification)
////        var notificationManager :NotificationManager =
////            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
////        if(notificationManager != null){
////            notificationManager.notify(getNotificationId(), notification);
////        }
//    }

    private fun getNotificationId(): Int {
        return Random.nextInt(1000);
    }

    override fun getListSuccess(locationList: ArrayList<Location>) {
        isEmpty.visibility = View.GONE;
        locationRecyclerView.visibility = View.VISIBLE;
        locationAdapter = LocationAdapter(this, locationList);
        locationRecyclerView.adapter = locationAdapter;
    }

    override fun getListError() {
        isEmpty.visibility = View.VISIBLE;
        locationRecyclerView.visibility = View.GONE;
    }
}