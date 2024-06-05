package com.example.emergencyguide.Evacuation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.ActivityEvacuateTipsBinding
import com.google.android.material.tabs.TabLayoutMediator

class EvacuateTipsActivity : AppCompatActivity() {
    lateinit var binding: ActivityEvacuateTipsBinding

    var diasterType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvacuateTipsBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        binding.vpEvaTips.adapter = TipsVPAdapter(this)
        diasterType = intent.getStringExtra("disaster")!!
        binding.tvEvacuateTips.text = diasterType + " 대피 요령"

        TabLayoutMediator(binding.tabEvaTips, binding.vpEvaTips) { tab, position ->
            tab.text = when (position) {
                0 -> if (diasterType == "지진") "평소 대비" else "화재가 울릴 때"
                1 -> if (diasterType == "지진") "지진 발생 시" else "화재대처"
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