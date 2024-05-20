package com.example.emergencyguide.EmergencyGuide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.emergencyguide.databinding.ActivityEmergencyGuideBinding

class EmergencyGuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyGuideBinding.inflate(layoutInflater)


        val composeView = binding.composeViewEmergencyGuide
        composeView.setContent {
            InitComposeContent()
        }

        setContentView(binding.root)

    }

    @Composable
    fun InitComposeContent(){
        Text(text = "여기는 응급처치 가이드!!!")
    }
}

