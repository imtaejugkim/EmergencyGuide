package com.example.emergencyguide.MedicineGuide

import Medicine
import MedicineInfo
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyguide.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import org.jsoup.Jsoup
import java.io.IOException

class MedicineDetailActivity : AppCompatActivity() {
    lateinit var medicineInfoList: ArrayList<MedicineInfo>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine_detail)

        medicineInfoList = ArrayList()

        CoroutineScope(Dispatchers.Main).launch {
            medicineInfoList = loadData() as ArrayList<MedicineInfo>
            updateUI()
        }

        //Log.d("MedicineDetailActivity", "$medicineInfoList")
        //Log.d("MedicineDetailActivity", "$position")

        val imageView = findViewById<ImageView>(R.id.back_button)

        imageView.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadData(): List<MedicineInfo> {
        val medicines = ArrayList<MedicineInfo>()

        val names = resources.getStringArray(R.array.name)
        val enterprises = resources.getStringArray(R.array.enterprise)
        val expirations = resources.getStringArray(R.array.expiration)
        val storages = resources.getStringArray(R.array.storage)
        val effects = resources.getStringArray(R.array.effect)
        val descriptions = resources.getStringArray(R.array.description)
        val warnings = resources.getStringArray(R.array.warning)

        for (i in names.indices) {
            val medicine = MedicineInfo().apply {
                name = names[i]
                enterprise = enterprises[i]
                expiration = expirations[i]
                storage = storages[i]
                effect = effects[i]
                description = descriptions[i]
                warning = warnings[i]
            }
            medicines.add(medicine)
        }

        return medicines
    }

    private suspend fun updateUI() {
        while (medicineInfoList.isEmpty()) {
            delay(100) // 0.1초마다 체크
        }

        val position = intent.getIntExtra("position", -1)

        val bigNameTextView = findViewById<TextView>(R.id.big_name)
        val medNameTextView = findViewById<TextView>(R.id.name)
        val medDescriptionTextView = findViewById<TextView>(R.id.description)
        val enterpriseTextView = findViewById<TextView>(R.id.enterprise)
        val expirationTextView = findViewById<TextView>(R.id.expiration)
        val storageTextView = findViewById<TextView>(R.id.storage)
        val effectTextView = findViewById<TextView>(R.id.effect)
        val warningTextView = findViewById<TextView>(R.id.warning)

        bigNameTextView.text = medicineInfoList[position].name
        medNameTextView.text = medicineInfoList[position].name
        medDescriptionTextView.text = medicineInfoList[position].description
        enterpriseTextView.text = medicineInfoList[position].enterprise
        expirationTextView.text = medicineInfoList[position].expiration
        storageTextView.text = medicineInfoList[position].storage
        effectTextView.text = medicineInfoList[position].effect
        warningTextView.text = medicineInfoList[position].warning
    }
}