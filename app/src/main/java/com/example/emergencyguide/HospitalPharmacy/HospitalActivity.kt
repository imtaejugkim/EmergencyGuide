package com.example.emergencyguide.HospitalPharmacy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
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
    private var addressData : ArrayList<String> = arrayListOf()
    private var phoneData : ArrayList<String> = arrayListOf()
    private var timeData : ArrayList<String> = arrayListOf()
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initMapView(savedInstanceState)
        initRecyclerView()
        initMoveRecyclerView()
        initShowDetail()
        initBack()
        initSearchView()

    }

    private fun initSearchView() {
        val searchView = binding.etHospitalSearch

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchAndMoveToHospital(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterHospitals(it)
                }
                return true
            }
        })
    }

    private fun filterHospitals(query: String) {
        val filteredList = hospitalData.filter {
            it.hospitalName.contains(query, ignoreCase = true)
        }
        hospitalAdapter.updateData(filteredList)
    }

    private fun searchAndMoveToHospital(query: String) {
        val position = hospitalData.indexOfFirst { it.hospitalName.contains(query, ignoreCase = true) }
        if (position != -1) {
            binding.rvHospital.smoothScrollToPosition(position)
            moveCameraToPosition(position)
        }
    }

    private fun initData() {
        addressData.add("서울특별시 광진구 능동로 120-1")
        addressData.add("화양동 번지 5층 5-106")
        addressData.add("서울특별시 광진구 화양동 아차산로 241 연한빌딩 7층")
        addressData.add("서울특별시 광진구 아차산로 237 삼진빌딩 가온정신건강의학과 의원 4층")
        addressData.add("서울특별시 광진구 자양제3동 580-13 4층")
        addressData.add("서울특별시 광진구 군자동 광나루로 361")
        addressData.add("서울특별시 광진구 자양제4동 8-1")
        addressData.add("서울특별시 광진구 아차산로 213 리즈온의원 건대점 3층")
        addressData.add("서울특별시 광진구 동일로 178")

        phoneData.add("1588-1533")
        phoneData.add("02-3409-7533")
        phoneData.add("02-469-3003")
        phoneData.add("02-464-6051")
        phoneData.add("02-464-6051")
        phoneData.add("02-469-5990")
        phoneData.add("02-466-3575")
        phoneData.add("02-463-1755")
        phoneData.add("02-6952-7737")

        timeData.add("오전 8:30 ~ 오후 5:30")
        timeData.add("오전 11:00 ~ 오후 9:00")
        timeData.add("오전 10:00 ~ 오후 9:00")
        timeData.add("오전 9:30 ~ 오후 7:00")
        timeData.add("영업시간 정보 없음")
        timeData.add("영업시간 정보 없음")
        timeData.add("오전 10:00 ~ 오후 8:30")
        timeData.add("영업시간 정보 없음")


    }

    private fun initBack() {
        binding.ivHospitalBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initShowDetail() {
        binding.clShowDetail.setOnClickListener {
            val selectedPosition = (binding.rvHospital.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (selectedPosition != RecyclerView.NO_POSITION && hospitalData.isNotEmpty()) {
                val selectedHospital = hospitalData[selectedPosition]
                val intent = Intent(this, HospitalDetailActivity::class.java)
                intent.putExtra("hospitalName", selectedHospital.hospitalName)
                intent.putExtra("hospitalImg", selectedHospital.hospitalImg)
                intent.putExtra("hospitalTime", selectedHospital.hospitalTime)
                intent.putExtra("hospitalLat", selectedHospital.hospitalLat)
                intent.putExtra("hospitalLng", selectedHospital.hospitalLng)
                Log.d("selectedPostion",selectedPosition.toString())

                intent.putExtra("hospitalAddress", addressData!![selectedPosition])
                intent.putExtra("hospitalPhone", phoneData[selectedPosition])
                startActivity(intent)
            }
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
            val defaultPhotoUrl = "https://cdn-icons-png.flaticon.com/128/527/527034.png"
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
                        val photoUrl = if (photoReference.isNullOrEmpty()) defaultPhotoUrl else "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=$apiKey"
                        val openingHours = timeData[i]
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
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
