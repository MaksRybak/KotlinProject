package com.example.kursova

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //Добавив маркери на карту по позиціях і назвою за кожним маркером
        mMap.addMarker(MarkerOptions().position(LatLng(49.840666, 24.022499)).title("Головний корпус"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.837107, 24.040142)).title("Факультет педагогічної освіти"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.839371, 24.028561)).title("Факультет управління фінансами та бізнесу"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.839689, 24.023232)).title("Факультет міжнародних відносин"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.839689, 24.023232)).title("Факультет міжнародних відносин"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.836445, 24.033420)).title("Навчальний корпус"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.832538, 24.030402)).title("Факультет хімії"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.842809, 24.028417)).title("Факультет економіки"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.832042, 24.030108)).title("Факультет фізики"))
        mMap.addMarker(MarkerOptions().position(LatLng(49.832160, 24.027383)).title("Факультет електроніки та комп'ютерних технологій"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(49.840666, 24.022499),14.0f)) //Центровий маркер
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
    }
}