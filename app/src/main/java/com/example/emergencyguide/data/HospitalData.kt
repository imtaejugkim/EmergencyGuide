package com.example.emergencyguide.data

data class HospitalData(
    val hospitalName : String,
    val hospitalImg : String,
    val hospitalTime : String,
    val hospitalLat : Double,
    val hospitalLng : Double,
    val distance: Double
)