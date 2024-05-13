package com.example.emergencyguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.EmergencyGuide.EmergencyGuideActivity
import com.example.emergencyguide.EmergencyNumber.EmergencyNumberActivity
import com.example.emergencyguide.Evacuation.EvacuationActivity
import com.example.emergencyguide.HospitalPharmacy.HospitalActivity
import com.example.emergencyguide.MedicineGuide.MedicineActivity
import com.example.emergencyguide.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.clMainEmergencyGuide.setOnClickListener {
            val intent = Intent(this, EmergencyGuideActivity::class.java)
            startActivity(intent)
        }

        binding.clMainEvacuation.setOnClickListener {
            val intent = Intent(this, EvacuationActivity::class.java)
            startActivity(intent)
        }

        binding.clMainHospital.setOnClickListener {
            val intent = Intent(this, HospitalActivity::class.java)
            startActivity(intent)
        }

        binding.clMainMedicine.setOnClickListener {
            val intent = Intent(this, MedicineActivity::class.java)
            startActivity(intent)
        }

        binding.clMainEmergencyNumber.setOnClickListener {
            val intent = Intent(this, EmergencyNumberActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}