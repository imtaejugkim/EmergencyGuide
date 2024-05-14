package com.example.emergencyguide.EmergencyNumber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.R

class EmergencyNumberExpandableAdapter (
    private val emergencyNumberList: List<EmergencyNumber>
) : RecyclerView.Adapter<EmergencyNumberExpandableAdapter.MyViewHolder>() {

    class MyViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(emergencyNumber: EmergencyNumber) {
//            val emerTitle = itemView.findViewById<TextView>(R.id.medicine_name)
            val emerTitle = itemView.findViewById<TextView>(R.id.emergency_number_title)

//            val emerInformation = itemView.findViewById<TextView>(R.id.medicine_description)
            val emerInformation = itemView.findViewById<TextView>(R.id.emergency_number_information)

//            val imgMore = itemView.findViewById<ImageView>(R.id.more_button)
            val emerImgMore = itemView.findViewById<ImageView>(R.id.emergency_number_more_button)
            val emerLayoutExpand = itemView.findViewById<LinearLayout>(R.id.emergency_number_layout_expand)

            emerTitle.text = emergencyNumber.title
            emerInformation.text = emergencyNumber.information

            emerImgMore.setOnClickListener {
                val show = toggleLayout(!emergencyNumber.isExpanded, it, emerLayoutExpand)
                emergencyNumber.isExpanded = show
            }
        }
        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout) : Boolean {
            EmergencyNumberToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                EmergencyNumberToggleAnimation.expand(layoutExpand)
            } else {
                EmergencyNumberToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.emergency_number_row, parent, false))
    }

    override fun getItemCount(): Int {
        return emergencyNumberList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(emergencyNumberList[position])
    }
}