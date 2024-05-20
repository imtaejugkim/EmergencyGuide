package com.example.emergencyguide.EmergencyNumber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.emergencyguide.databinding.ActivityEmergencyNumberBinding

class EmergencyNumberActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEmergencyNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyNumberBinding.inflate(layoutInflater)

        val composeView = binding.composeViewEmergencyNumber
        composeView.setContent {
            InitComposeContent()
        }

        setContentView(binding.root)
    }

    @Composable
    private fun InitComposeContent() {
        Text(text = "Hello from Compose!")
    }
}