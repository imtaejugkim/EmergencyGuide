package com.example.emergencyguide.HospitalPharmacy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.data.HospitalData
import com.example.emergencyguide.databinding.ActivityHospitalBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class HospitalActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityHospitalBinding
    private lateinit var mapView: MapView
    private lateinit var hospitalAdapter: HospitalAdapter
    private var hospitalData: ArrayList<HospitalData> = arrayListOf()
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMapView(savedInstanceState)
        initRecyclerView()
        initMoveRecyclerView()
//        initShowDetail()
    }

    private fun initShowDetail() {
        binding.clShowDetail.setOnClickListener {

        }
    }

    private fun initRecyclerView() {
        hospitalAdapter = HospitalAdapter(this, hospitalData)
        binding.rvHospital.adapter = hospitalAdapter
        binding.rvHospital.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(binding.rvHospital)
    }

    private fun initMoveRecyclerView() {
        binding.rvHospital.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        moveCameraToPosition(position)
                    }
                }
            }
        })
    }

    private fun initMapView(inst: Bundle?) {
        mapView = binding.mvHospital
        mapView.onCreate(inst)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.setOnMarkerClickListener { marker ->
            val position = marker.tag as Int
            binding.rvHospital.smoothScrollToPosition(position)
            true
        }
        fetchNearbyPlaces()
    }

    private fun fetchNearbyPlaces() {
        val baseLocation = LatLng(37.5431, 127.0764)
        val apiKey = "AIzaSyDah3TDwA26PzqpNtFuGq9EI9G3GB1Efhg"
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=${baseLocation.latitude},${baseLocation.longitude}" +
                "&radius=1000" +
                "&type=hospital" +
                "&key=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HospitalActivity", e.toString(), e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let { responseBody ->
                    val responseString = responseBody.string()
                    val jsonObject = JSONObject(responseString)
                    val results = jsonObject.getJSONArray("results")
                    val tempHospitalData = mutableListOf<HospitalData>()
                    for (i in 0 until results.length()) {
                        val result = results.getJSONObject(i)
                        val name = result.getString("name")
                        val location = result.getJSONObject("geometry").getJSONObject("location")
                        val latitude = location.getDouble("lat")
                        val longitude = location.getDouble("lng")
                        val photoReference = result.optJSONArray("photos")?.getJSONObject(0)?.getString("photo_reference") ?: ""
                        val photoUrl = if (photoReference.isNotEmpty()) "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=$apiKey" else ""
                        val openingHours = result.optJSONObject("opening_hours")?.getString("open_now") ?: "Unknown"
                        val distance = Math.sqrt(Math.pow(latitude - baseLocation.latitude, 2.0) + Math.pow(longitude - baseLocation.longitude, 2.0))
                        val hospital = HospitalData(name, photoUrl, openingHours, latitude, longitude, distance)
                        tempHospitalData.add(hospital)
                    }
                    tempHospitalData.sortBy { it.distance }
                    runOnUiThread {
                        hospitalData.clear()
                        hospitalData.addAll(tempHospitalData)
                        hospitalAdapter.notifyDataSetChanged()
                        if (hospitalData.isNotEmpty()) {
                            moveCameraToPosition(0)
                        }
                    }
                }
            }
        })
    }

    private fun moveCameraToPosition(position: Int) {
        val hospital = hospitalData[position]
        val latLng = LatLng(hospital.hospitalLat, hospital.hospitalLng)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        if (currentMarker != null) {
            currentMarker?.remove()
        }
        currentMarker = googleMap.addMarker(MarkerOptions().position(latLng).title(hospital.hospitalName))
        currentMarker?.tag = position
        currentMarker?.showInfoWindow()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
}
