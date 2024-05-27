package com.example.emergencyguide.Evacuation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.ActivityEvacuationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EvacuationActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityEvacuationBinding
    lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvacuationBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        initMapView(savedInstanceState)
        setListeners()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initMapView(inst: Bundle?) {
        this.mapView = binding.mvEvacuation
        mapView.onCreate(inst)
        mapView.getMapAsync(this@EvacuationActivity)
    }

    private fun setListeners() {
        // back button 클릭 시 activity 종료
        binding.ivEvaBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val university = LatLng(37.5431, 127.0764)
        googleMap.addMarker(
            MarkerOptions()
                .position(university)
                .title("건국대학교")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(university, 16f))

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
}