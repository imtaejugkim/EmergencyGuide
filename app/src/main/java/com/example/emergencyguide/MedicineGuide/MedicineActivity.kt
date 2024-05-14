package com.example.emergencyguide.MedicineGuide

import Medicine
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.R

class MedicineActivity : AppCompatActivity() {

    private lateinit var medicineList: List<Medicine>
    private lateinit var adapter: MedicineExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_medicine)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.emergency_number_list)

        medicineList = ArrayList()
        medicineList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicineExpandableAdapter(medicineList)
        recyclerView.adapter = adapter
    }

    private fun loadData() : List<Medicine> {
        val medicineList = ArrayList<Medicine>()

        val medicines = resources.getStringArray(R.array.medicine_name)
        val descriptions = resources.getStringArray(R.array.medicine_description)

        for (i in medicines.indices) {
            val medicine = Medicine().apply {
                name = medicines[i]
                description = descriptions[i]
            }
            medicineList.add(medicine)
        }
        return medicineList
    }
}