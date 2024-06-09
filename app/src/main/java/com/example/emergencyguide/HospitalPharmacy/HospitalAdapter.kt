package com.example.emergencyguide.HospitalPharmacy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emergencyguide.data.HospitalData
import com.example.emergencyguide.databinding.ItemHospitalBinding

class HospitalAdapter(val context: Context, private val hospitalData: ArrayList<HospitalData>) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: HospitalData){
            binding.tvHospitalName.text = data.hospitalName
            Glide.with(context)
                .load(data.hospitalImg)
                .into(binding.ivHospital)
            binding.tvHospitalNameTitle.text = data.hospitalName
            binding.tvHospitalTime.text = data.hospitalTime

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdapter.ViewHolder {
        val binding = ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = hospitalData.size

    override fun onBindViewHolder(holder: HospitalAdapter.ViewHolder, position: Int) {
        holder.bind(hospitalData[position])
    }

    fun updateData(newData: List<HospitalData>) {
        hospitalData.clear()
        hospitalData.addAll(newData)
        notifyDataSetChanged()
    }
}