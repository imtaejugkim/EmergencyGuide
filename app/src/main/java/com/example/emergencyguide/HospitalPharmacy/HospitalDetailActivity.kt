package com.example.emergencyguide.HospitalPharmacy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.example.emergencyguide.data.HospitalReviewData
import com.example.emergencyguide.databinding.ActivityHospitalDetailBinding

class HospitalDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalDetailBinding
    private lateinit var hospitalReviewAdapter: HospitalReviewAdapter
    private var hospitalReviewData: ArrayList<HospitalReviewData> = arrayListOf()
    private lateinit var activityResultLauncher:  ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hospitalName = intent.getStringExtra("hospitalName")
        val hospitalImg = intent.getStringExtra("hospitalImg")
        val hospitalTime = intent.getStringExtra("hospitalTime")
        val hospitalLat = intent.getDoubleExtra("hospitalLat", 0.0)
        val hospitalLng = intent.getDoubleExtra("hospitalLng", 0.0)
        val hospitalAddress = intent.getStringExtra("hospitalAddress") ?: "서울시"
        val hospitalPhone = intent.getStringExtra("hospitalPhone") ?: "02-450-4456"

        initImg()
        initSetting(hospitalName!!,hospitalImg!!,hospitalTime!!,hospitalLat,hospitalLng, hospitalAddress!!, hospitalPhone!!)
        initData()
        initRecyclerView()
        initPost()
        initBack()
        setActivityResult()

    }

    private fun initImg() {
        Glide.with(this)
            .load("https://cdn-icons-png.flaticon.com/128/67/67872.png")
            .into(binding.ivAddress)

        Glide.with(this)
            .load("https://cdn-icons-png.flaticon.com/128/7764/7764301.png")
            .into(binding.ivHours)

        Glide.with(this)
            .load("https://cdn-icons-png.flaticon.com/128/483/483969.png")
            .into(binding.ivPhone)
    }

    private fun setActivityResult() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val reviewText = it.getStringExtra("reviewText") ?: return@let
                    val reviewName = it.getStringExtra("reviewName") ?: return@let
                    val newReview = HospitalReviewData(reviewName, "이미지 URL", reviewText)
                    hospitalReviewData.add(0, newReview)
                    hospitalReviewAdapter.notifyItemInserted(0)
                    binding.rvHospitalReview.scrollToPosition(0)
                }
            }
        }
    }

    private fun initSetting(
        hospitalName: String,
        hospitalImg: String,
        hospitalTime: String,
        hospitalLat: Double,
        hospitalLng: Double,
        hospitalAddress : String,
        hospitalPhone : String
    ) {
        binding.tvHospitalTitle.text = hospitalName
        binding.tvAddress.text = hospitalAddress
        binding.tvHouss.text = hospitalTime
        binding.tvPhone.text = hospitalPhone
        Glide.with(this)
            .load(hospitalImg)
            .into(binding.ivHospital)

    }


    private fun initBack() {
        binding.ivHospitalBack.setOnClickListener {
            onBackPressed()
        }
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
            activityResultLauncher.launch(intent)  // ReviewPostActivity를 호출
        }
    }
}