package com.example.emergencyguide.HospitalPharmacy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emergencyguide.data.HospitalReviewData
import com.example.emergencyguide.databinding.ItemHospitalReviewBinding

class HospitalReviewAdapter(val context: Context, private val reviewData: ArrayList<HospitalReviewData>) : RecyclerView.Adapter<HospitalReviewAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemHospitalReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: HospitalReviewData){
            binding.tvReviewUser.text = data.userName
            Glide.with(context)
                .load(data.userImg)
                .into(binding.ivReviewUser)
            binding.tvReview.text = data.userReview
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalReviewAdapter.ViewHolder {
        val binding = ItemHospitalReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewData.size

    override fun onBindViewHolder(holder: HospitalReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviewData[position])
    }
}