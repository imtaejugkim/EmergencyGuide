package com.example.emergencyguide.Evacuation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.databinding.ItemEvacuateTipBinding

class EvacuateTipsAdapter(val context: Context, private val evacuateTipsData: ArrayList<EvacuateTipsData>): RecyclerView.Adapter<EvacuateTipsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemEvacuateTipBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EvacuateTipsData) {
            binding.tvEvaTipTitle.text = data.title
            binding.tvEvaTipContents.text = data.contents
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EvacuateTipsAdapter.ViewHolder {
        val binding = ItemEvacuateTipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EvacuateTipsAdapter.ViewHolder, position: Int) {
        holder.bind(evacuateTipsData[position])
    }

    override fun getItemCount(): Int = evacuateTipsData.size

}

data class EvacuateTipsData(
    val title: String,
    val contents: String
)