package com.example.emergencyguide.MedicineGuide

import Medicine
import MedicineInfo
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.R

class MedicineExpandableAdapter (
    private val medicineList: List<Medicine>,
    private val medicineInfoList: List<MedicineInfo>
) : RecyclerView.Adapter<MedicineExpandableAdapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(medicine: Medicine, medicineInfo: MedicineInfo) {
            val medName = itemView.findViewById<TextView>(R.id.medicine_name)
            val medDescription = itemView.findViewById<TextView>(R.id.medicine_description)
            val imgMore = itemView.findViewById<ImageView>(R.id.more_button)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.medicine_layout_expand)
            val detail = itemView.findViewById<Button>(R.id.detail)

            medName.text = medicine.name
            medDescription.text = medicine.description

            detail.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MedicineDetailActivity::class.java)
                intent.putExtra("position", adapterPosition)
                //Log.d("MedicineExpandableAdapter", "$adapterPosition")
                context.startActivity(intent)
            }
            imgMore.setOnClickListener {
                val show = toggleLayout(!medicine.isExpanded, it, layoutExpand)
                medicine.isExpanded = show
            }
        }
        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout) : Boolean {
            MedicineToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                MedicineToggleAnimation.expand(layoutExpand)
            } else {
                MedicineToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.medicine_row, parent, false))
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(medicineList[position], medicineInfoList[position])
    }
}