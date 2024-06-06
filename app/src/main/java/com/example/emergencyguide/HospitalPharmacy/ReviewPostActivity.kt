package com.example.emergencyguide.HospitalPharmacy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.databinding.ActivityReviewPostBinding

class ReviewPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewPostBinding.inflate(layoutInflater)

        initBack()
        binding.clSubmit.setOnClickListener {
            initSubmit()
        }

        setContentView(binding.root)
    }

    private fun initSubmit() {
        val reviewName = binding.etReviewName.text.toString()
        val reviewText = binding.etReviewPost.text.toString()
        val resultIntent = Intent()
        resultIntent.putExtra("reviewText", reviewText)
        resultIntent.putExtra("reviewName", reviewName)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initBack() {
        binding.ivHospitalBack.setOnClickListener {
            onBackPressed()
        }
    }
}