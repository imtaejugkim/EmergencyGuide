package com.example.emergencyguide.HospitalPharmacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencyguide.data.HospitalData
import com.example.emergencyguide.databinding.ActivityHospitalBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HospitalActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityHospitalBinding
    private lateinit var mapView: MapView
    private lateinit var hospitalAdapter: HospitalAdapter
    private var hospitalData : ArrayList<HospitalData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)

        initMapView(savedInstanceState)
        initDummyData()
        initRecyclerView()

        setContentView(binding.root)
    }

    private fun initDummyData() {
        hospitalData.addAll(
            arrayListOf(
                HospitalData("병원1","https://lh5.googleusercontent.com/p/AF1QipMENO7As_VGJrVv7OJ-JDgrAu6CS5nYPQuVYX4s=w408-h306-k-no","금일 휴무"),
                HospitalData("병원2","https://lh5.googleusercontent.com/p/AF1QipPQXJya7_tyq3ydOnyNAf6PNNRyfjVW5rQeEBTN=w408-h544-k-no","금일 휴무"),
                HospitalData("병원3","https://lh5.googleusercontent.com/p/AF1QipNABm6Z07j08zjc8cM4MezarKLjf6moQtVJ2H-7=w408-h408-k-no","금일 휴무"),
                HospitalData("병원4","https://lh5.googleusercontent.com/p/AF1QipNuj6EdcCbewkG4kcBT0UHUPlh6GGGUAKl8Gm-b=w408-h352-k-no","금일 휴무"),
                HospitalData("병원5","https://lh5.googleusercontent.com/p/AF1QipNviur--vALuJ9D0yd8YJmlpHJLMcN3VbvjxRkZ=w408-h544-k-no","금일 휴무"),
            )
        )
    }

    private fun initRecyclerView() {
        hospitalAdapter = HospitalAdapter(this, hospitalData)
        binding.rvHospital.adapter = hospitalAdapter
        binding.rvHospital.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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
