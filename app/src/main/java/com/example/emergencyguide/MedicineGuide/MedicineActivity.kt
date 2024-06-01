package com.example.emergencyguide.MedicineGuide

import Medicine
import MedicineInfo
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyguide.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException

class MedicineActivity : AppCompatActivity() {

    private lateinit var medicineList: ArrayList<Medicine>
    private lateinit var medicineInfoList: List<MedicineInfo>
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

        val recyclerView = findViewById<RecyclerView>(R.id.medicine_list)

        medicineList = ArrayList()
        loadData1()
        medicineInfoList = loadData2()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicineExpandableAdapter(medicineList, medicineInfoList)
        recyclerView.adapter = adapter

        val imageView = findViewById<ImageView>(R.id.ic_back_button)
        imageView.setOnClickListener {
            onBackPressed()
        }

        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun loadData1() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doc1 = Jsoup.connect("https://nedrug.mfds.go.kr/pbp/CCBCA01/getList?totalPages=2&page=1&limit=10&sort=&sortOrder=&searchYn=&itemName=&entpName=")
                    .get()
                val doc2 = Jsoup.connect("https://nedrug.mfds.go.kr/pbp/CCBCA01/getList?totalPages=2&page=2&limit=10&sort=&sortOrder=&searchYn=&itemName=&entpName=")
                    .get()
                val elements1 = doc1.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#list table.tb_list.th-shot tbody tr")
                val elements2 = doc2.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#list table.tb_list.th-shot tbody tr")

                var name: String = ""
                var i = 0
                val descriptions = resources.getStringArray(R.array.description)
                for (ele in elements1) {
                    val firstElement = ele.select("td.al_l span span.blue_cr a").first()
                    if (firstElement != null) {
                        name = firstElement.text()
                    }

                    medicineList.add(Medicine(name, descriptions[i], false))
                    i++
                    //medicineInfoList.add(MedicineInfo(name, "enterprise", "expiration", "storage", "effect", description, "warning"))
                }
                for (ele in elements2) {
                    val firstElement = ele.select("td.al_l span span.blue_cr a").first()
                    if (firstElement != null) {
                        name = firstElement.text()
                    }

                    medicineList.add(Medicine(name, descriptions[i], false))
                    i++
                    //medicineInfoList.add(MedicineInfo(name, "enterprise", "expiration", "storage", "effect", description, "warning"))
                }

                withContext(Dispatchers.Main) {
                    this@MedicineActivity.medicineList = medicineList
                    adapter.notifyDataSetChanged()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun loadData2(): List<MedicineInfo> {
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

}