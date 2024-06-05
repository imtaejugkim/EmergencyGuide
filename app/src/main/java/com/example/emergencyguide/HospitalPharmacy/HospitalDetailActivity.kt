package com.example.emergencyguide.HospitalPharmacy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.emergencyguide.data.HospitalReviewData
import com.example.emergencyguide.databinding.ActivityHospitalDetailBinding

class HospitalDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalDetailBinding
    private lateinit var hospitalReviewAdapter: HospitalReviewAdapter
    private var hospitalReviewData: ArrayList<HospitalReviewData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initRecyclerView()
        initPost()

    }

    private fun initData() {
        hospitalReviewData.addAll(
            arrayListOf(
                HospitalReviewData("김태정","https://cdn.pixabay.com/photo/2024/03/02/13/05/orange-parrots-8608540_1280.jpg","병원이 쾌적해요"),
                HospitalReviewData("구서정","https://cdn.pixabay.com/photo/2024/03/02/13/05/orange-parrots-8608540_1280.jpg","약국이 쾌적해요"),
                HospitalReviewData("김종권","https://cdn.pixabay.com/photo/2024/03/02/13/05/orange-parrots-8608540_1280.jpg","병원이 웅장해요"),
                HospitalReviewData("조하상","https://cdn.pixabay.com/photo/2024/03/02/13/05/orange-parrots-8608540_1280.jpg","병원이 깨끗해요"),
                HospitalReviewData("유가은","https://cdn.pixabay.com/photo/2024/03/02/13/05/orange-parrots-8608540_1280.jpg","병원이 신기해요"),
                )
        )
    }

    private fun initRecyclerView() {
        hospitalReviewAdapter = HospitalReviewAdapter(this, hospitalReviewData)
        binding.rvHospitalReview.adapter = hospitalReviewAdapter
        binding.rvHospitalReview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(binding.rvHospitalReview)
    }

    private fun initPost() {
        binding.clReviewPost.setOnClickListener {
            val intent = Intent(this, ReviewPostActivity::class.java)
        }
    }
}