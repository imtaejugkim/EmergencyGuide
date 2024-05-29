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

data class Location(
    val name: String,
    val latLng: LatLng
)
class EvacuationActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityEvacuationBinding
    lateinit var mapView: MapView
    private var evacuationList = mutableListOf<Location>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvacuationBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        setDummy()
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(university, 16f))

        for (location in evacuationList) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(location.latLng)
                    .title(location.name)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    private fun setDummy() {
        evacuationList = mutableListOf(
            Location(name = "군자지하보도", latLng = LatLng(37.5600853, 127.0737223)),
            Location(name = "신성그랜드타워 지하주차장", latLng = LatLng(37.5650621660135, 127.080415923364)),
            Location(name = "군자역", latLng = LatLng(37.5573631939521, 127.079017279019)),
            Location(name = "광진경찰서", latLng = LatLng(37.5429114164928, 127.083902174384)),
            Location(name = "구의 1동 주민센터", latLng = LatLng(37.5424132407312, 127.085677335897)),
            Location(name = "어린이대공원역", latLng = LatLng(37.547871203305, 127.074549925093)),
            Location(name = "어린이회관", latLng = LatLng(37.5472460029599, 127.078304434841)),
            Location(name = "동서울터미널 지하주차장 1층", latLng = LatLng(37.5345385101597, 127.094184050922)),
            Location(name = "한국중앙교회", latLng = LatLng(37.5685928714227, 127.084830745395))
        )
    }
}