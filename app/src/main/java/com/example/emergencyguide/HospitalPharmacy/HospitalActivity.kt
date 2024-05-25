package com.example.emergencyguide.HospitalPharmacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.databinding.ActivityHospitalBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HospitalActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityHospitalBinding
    lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)

        initMapView(savedInstanceState)

        setContentView(binding.root)
    }

    private fun initMapView(inst: Bundle?) {
        this.mapView = binding.mvHospital
        mapView.onCreate(inst)
        mapView.getMapAsync(this@HospitalActivity)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.5431, 127.0764))
                .title("건국대학교")
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

}
