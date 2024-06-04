package com.example.emergencyguide.HospitalPharmacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.databinding.ActivityHospitalDetailBinding

class HospitalDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}