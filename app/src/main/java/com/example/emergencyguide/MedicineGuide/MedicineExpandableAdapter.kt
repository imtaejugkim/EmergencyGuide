package com.example.emergencyguide.MedicineGuide

import Medicine
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.R

class MedicineExpandableAdapter (
    private val medicineList: List<Medicine>
) : RecyclerView.Adapter<MedicineExpandableAdapter.MyViewHolder>() {

    class MyViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(medicine: Medicine) {
            val medName = itemView.findViewById<TextView>(R.id.medicine_name)
            val medDescription = itemView.findViewById<TextView>(R.id.medicine_description)
            val imgMore = itemView.findViewById<ImageView>(R.id.more_button)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.medicine_layout_expand)

            medName.text = medicine.name
            medDescription.text = medicine.description

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
        holder.bind(medicineList[position])
    }
}