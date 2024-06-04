package com.example.emergencyguide.Evacuation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.ActivityEvacuateTipsBinding
import com.google.android.material.tabs.TabLayoutMediator

class EvacuateTipsActivity : AppCompatActivity() {
    lateinit var binding: ActivityEvacuateTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvacuateTipsBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        binding.vpEvaTips.adapter = TipsVPAdapter(this)

        TabLayoutMediator(binding.tabEvaTips, binding.vpEvaTips) { tab, position ->
            tab.text = when (position) {
                0 -> "평소 대비"
                1 -> "지진 발생 시"
                else -> null
            }
        }.attach()

        setListeners()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setListeners() {
        binding.ivEvaTipsBack.setOnClickListener {
            finish()
        }
    }
}