package com.example.emergencyguide.Evacuation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.databinding.ActivityEvacuateTipsBinding
import com.google.android.material.tabs.TabLayoutMediator

class EvacuateTipsActivity : AppCompatActivity() {
    lateinit var binding: ActivityEvacuateTipsBinding
    private var disasterType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvacuateTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
        setListeners()
    }

    private fun setViewPager() {
        binding.vpEvaTips.adapter = TipsVPAdapter(this)
        disasterType = intent.getStringExtra("disaster")!!
        binding.tvEvacuateTips.text = disasterType + " 행동 요령"

        TabLayoutMediator(binding.tabEvaTips, binding.vpEvaTips) { tab, position ->
            tab.text = when (position) {
                0 -> if (disasterType == "지진") "평소 대비" else "화재가 울릴 때"
                1 -> if (disasterType == "지진") "지진 발생 시" else "화재대처"
                else -> null
            }
        }.attach()
    }
    private fun setListeners() {
        binding.ivEvaTipsBack.setOnClickListener {
            finish()
        }
    }
}