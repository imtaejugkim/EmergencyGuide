package com.example.emergencyguide.EmergencyNumber

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.emergencyguide.databinding.ActivityEmergencyNumberBinding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Looks3
import androidx.compose.material.icons.filled.Looks4
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material.icons.filled.Waves
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class EmergencyNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyNumberBinding
    private val tabsList = listOf("긴급", "비긴급")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyNumberBinding.inflate(layoutInflater)

        val composeView = binding.composeViewEmergencyNumber
        composeView.setContent {
            InitComposeContent()
        }

        setContentView(binding.root)
    }

    @Composable
    private fun InitComposeContent() {
        val selectedTabIndex = remember { mutableStateOf(0) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                TopAppBar(
                    title = { Text(text = "긴급 연락처") },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    elevation = 0.dp
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) { // 왼쪽 정렬
                    TabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        backgroundColor = Color.Transparent, // 배경 투명하게
                        contentColor = Color.Black // 글자 검은색으로
                    ) {
                        tabsList.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = selectedTabIndex.value == index,
                                onClick = { selectedTabIndex.value = index }
                            )
                        }
                    }
                }

                when (selectedTabIndex.value) {
                    0 -> {
                        EmergencyContact(Icons.Default.LocalFireDepartment, "119", "화재")
                        EmergencyContact(Icons.Default.LocalHospital, "119", "응급실")
                        EmergencyContact(Icons.Default.LocalPolice, "112", "경찰")
                        EmergencyContact(Icons.Default.Waves, "122", "해양사고")
                    }
                    1 -> {
                        EmergencyContact(Icons.Default.LooksOne, "010-1111-1111", "비긴급 1번")
                        EmergencyContact(Icons.Default.LooksTwo, "010-2222-2222", "비긴급 2번")
                        EmergencyContact(Icons.Default.Looks3, "010-3333-3333", "비긴급 3번")
                        EmergencyContact(Icons.Default.Looks4, "010-4444-4444", "비긴급 4번")
                    }
                }
            }
        }
    }


    @Composable
    fun EmergencyContact(icon: androidx.compose.ui.graphics.vector.ImageVector, number: String, title: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = number, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
                    Text(text = title)
                }
                IconButton(onClick = { /* call action 적기 */ }) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}