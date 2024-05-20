package com.example.emergencyguide.MedicineGuide

import Medicine
import MedicineInfo
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
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

    lateinit var medicineList: ArrayList<Medicine>
    lateinit var medicineInfoList: ArrayList<MedicineInfo>
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
        medicineInfoList = ArrayList()
        loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicineExpandableAdapter(medicineList, medicineInfoList)
        recyclerView.adapter = adapter

        val imageView = findViewById<ImageView>(R.id.ic_back_button)
        imageView.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doc1 = Jsoup.connect("https://nedrug.mfds.go.kr/pbp/CCBCA01/getList?totalPages=2&page=1&limit=10&sort=&sortOrder=&searchYn=&itemName=&entpName=")
                    .get()
                val doc2 = Jsoup.connect("https://nedrug.mfds.go.kr/pbp/CCBCA01/getList?totalPages=2&page=2&limit=10&sort=&sortOrder=&searchYn=&itemName=&entpName=")
                    .get()
                val elements1 = doc1.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#list table.tb_list.th-shot tbody tr")
                val elements2 = doc2.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#list table.tb_list.th-shot tbody tr")

                var name: String = ""
                var link: String = ""
                //var enterprise: String = ""
                //var expiration: String = ""
                //var storage: String = ""
                //var effect: String = ""
                var description: String = ""
                //var warning: String = ""
                for (ele in elements1) {
                    val firstElement = ele.select("td.al_l span span.blue_cr a").first()
                    if (firstElement != null) {
                        name = firstElement.text()
                        link = firstElement.absUrl("href")
                        //Log.d("MedicineActivity", "Name: $name, Link: $link")
                    }
//                    val secondElement = ele.select("td.al_l span.s-th:contains(업체명) + span").first()
//                    if (secondElement != null) {
//                        enterprise = secondElement.text()
//                        Log.d("MedicineActivity", "Enterprise: $enterprise")
//                    }
//                    val thirdElement = ele.select("td span.s-th:contains(유효기간) + span").first()
//                    if (thirdElement != null) {
//                        expiration = thirdElement.text()
//                        Log.d("MedicineActivity", "Expiration: $expiration")
//                    }
//                    val fourthElement = ele.select("td.al_l span.s-th:contains(저장방법) + span").first()
//                    if (fourthElement != null) {
//                        storage = fourthElement.text()
//                        Log.d("MedicineActivity", "Storage: $storage")
//                    }

                    val doc = Jsoup.connect(link).get()
                    val elements = doc.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#view table.tb_base tbody tr")
//                    val fifthElement = elements.select("th:contains(효능효과)").first()
//                    if (fifthElement != null) {
//                        val fElement = fifthElement.nextElementSibling()?.select("p.indent0")?.first()
//                        if (fElement != null) {
//                            effect = fElement.text()
//                            Log.d("MedicineActivity", "Effect: $effect")
//                        }
//                    }
                    val sixthElement = elements.select("th:contains(용법용량)").first()
                    if (sixthElement != null) {
                        val sElement = sixthElement.nextElementSibling()
                        if (sElement != null) {
                            description = sElement.text()
                            //Log.d("MedicineActivity", "Description: $description")
                        }
                    }
//                    val seventhElement = elements.select("th:contains(주의사항)").first()
//                    if (seventhElement != null) {
//                        val seElement = seventhElement.nextElementSibling()
//                        if (seElement != null) {
//                            warning = seElement.text()
//                            Log.d("MedicineActivity", "Warning: $warning")
//                        }
//                    }

                    medicineList.add(Medicine(name, link, description, false))
                    medicineInfoList.add(MedicineInfo(name, "enterprise", "expiration", "storage", "effect", description, "warning"))
                }
                for (ele in elements2) {
                    val firstElement = ele.select("td.al_l span span.blue_cr a").first()
                    if (firstElement != null) {
                        name = firstElement.text()
                        link = firstElement.absUrl("href")
                        //Log.d("MedicineActivity", "Name: $name, Link: $link")
                    }
//                    val secondElement = ele.select("td.al_l span.s-th:contains(업체명) + span").first()
//                    if (secondElement != null) {
//                        enterprise = secondElement.text()
//                        Log.d("MedicineActivity", "Enterprise: $enterprise")
//                    }
//                    val thirdElement = ele.select("td span.s-th:contains(유효기간) + span").first()
//                    if (thirdElement != null) {
//                        expiration = thirdElement.text()
//                        Log.d("MedicineActivity", "Expiration: $expiration")
//                    }
//                    val fourthElement = ele.select("td.al_l span.s-th:contains(저장방법) + span").first()
//                    if (fourthElement != null) {
//                        storage = fourthElement.text()
//                        Log.d("MedicineActivity", "Storage: $storage")
//                    }

                    val doc = Jsoup.connect(link).get()
                    val elements = doc.select("div.container_wrap div#container div#content_wrap div#content div#con_body div#view table.tb_base tbody tr")
//                    val fifthElement = elements.select("th:contains(효능효과)").first()
//                    if (fifthElement != null) {
//                        val fElement = fifthElement.nextElementSibling()?.select("p.indent0")?.first()
//                        if (fElement != null) {
//                            effect = fElement.text()
//                            Log.d("MedicineActivity", "Effect: $effect")
//                        }
//                    }
                    val sixthElement = elements.select("th:contains(용법용량)").first()
                    if (sixthElement != null) {
                        val sElement = sixthElement.nextElementSibling()?.select("p.indent0")?.first()
                        if (sElement != null) {
                            description = sElement.text()
                            //Log.d("MedicineActivity", "Description: $description")
                        }
                    }
//                    val seventhElement = elements.select("th:contains(주의사항)").first()
//                    if (seventhElement != null) {
//                        val seElement = seventhElement.nextElementSibling()
//                        if (seElement != null) {
//                            warning = seElement.text()
//                            Log.d("MedicineActivity", "Warning: $warning")
//                        }
//                    }

                    medicineList.add(Medicine(name, link, description, false))
                    medicineInfoList.add(MedicineInfo(name, "enterprise", "expiration", "storage", "effect", description, "warning"))
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

}