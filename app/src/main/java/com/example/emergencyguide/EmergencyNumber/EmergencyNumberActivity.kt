package com.example.emergencyguide.EmergencyNumber


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.emergencyguide.R

class EmergencyNumberActivity : AppCompatActivity() {


    private lateinit var emergencyNumberList: List<EmergencyNumber>
    private lateinit var adapter: EmergencyNumberExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emergency_number)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.emergency_number_list)

        emergencyNumberList = ArrayList()
        emergencyNumberList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmergencyNumberExpandableAdapter(emergencyNumberList)
        recyclerView.adapter = adapter
    }

    private fun loadData() : List<EmergencyNumber> {
        val emergencyNumberList = ArrayList<EmergencyNumber>()

        val emergencyNumbers = resources.getStringArray(R.array.emergency_number_title)
        val informations = resources.getStringArray(R.array.emergency_number_information)

        for (i in emergencyNumbers.indices) {
            val emergencyNumber = EmergencyNumber().apply {
                title = emergencyNumbers[i]
                information = informations[i]
            }
            emergencyNumberList.add(emergencyNumber)
        }
        return emergencyNumberList
    }
}


