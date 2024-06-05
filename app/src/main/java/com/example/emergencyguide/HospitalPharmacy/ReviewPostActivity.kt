package com.example.emergencyguide.HospitalPharmacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.databinding.ActivityReviewPostBinding

class ReviewPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewPostBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }
}