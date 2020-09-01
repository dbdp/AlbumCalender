package com.android.app.albumcalender

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //clear button
        findViewById<Button>(R.id.clear_button).setOnClickListener {
            mMap.clear()
        }

        //show pin button
        findViewById<Button>(R.id.show_pin_button).setOnClickListener {
            val lat = findViewById<EditText>(R.id.latitude_text).text.toString().toDouble()
            val lon = findViewById<EditText>(R.id.longtitude_text).text.toString().toDouble()
            setPin(lat, lon)
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val list = arrayListOf<Pair<Double, Double>>(
            Pair(37.262877, 127.026297),
            Pair(37.262740, 127.021544),
            Pair(37.259385, 127.021844),
            Pair(37.270824, 127.031787)
        )
        list.forEach {
            setPin(it.first, it.second)
        }
    }

    private fun setPin(var1: Double, var2: Double) {
        val sydney = LatLng(var1, var2)
        // 아이콘 자체를 >>> <<< 이런식으로 시간순으로도 보여줄 수 있을 것 같다.
        val icon = BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)
        // addMarker가 Marker를 반환하고 remove로 marker를 삭제할 수 있다. marker 저장해서 관리 따로 필요하다.
        mMap.addMarker(MarkerOptions().position(sydney).title("sechan_test_app").icon(icon))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}