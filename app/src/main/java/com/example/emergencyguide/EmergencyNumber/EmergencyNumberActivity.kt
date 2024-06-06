package com.example.emergencyguide.EmergencyNumber

import android.content.Context
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Looks3
import androidx.compose.material.icons.filled.Looks4
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material.icons.filled.Waves
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.emergencyguide.EmergencyNumber.composables.AddandDeleteButtons
import com.example.emergencyguide.EmergencyNumber.composables.CreatePersonalDialog
import com.example.emergencyguide.EmergencyNumber.composables.EmergencyContact
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class EmergencyNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyNumberBinding
    private val tabsList = listOf("국가", "개인")

    // 연락처
    private val contacts = mutableStateListOf<Triple<String, String, MutableState<Boolean>>>()

    // 파일에 작성하는 함수
    private fun writeToFile(context: Context, data: List<String>) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("contacts.txt", MODE_PRIVATE))
            outputStreamWriter.write(data.joinToString("\n"))
            outputStreamWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readFromFile(): List<String> {
        val file = File(filesDir, "contacts.txt")
        if (!file.exists()) {
            return emptyList()
        }

        val inputStream = openFileInput("contacts.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val contacts = mutableListOf<String>()
        var line = reader.readLine()

        while (line != null) {
            contacts.add(line)
            line = reader.readLine()
        }

        inputStream.close()
        return contacts
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyNumberBinding.inflate(layoutInflater)

        // 텍스트 파일에서 연락처 불러오기
        val contactData = readFromFile()
        contactData.forEach { data ->
            val parts = data.split(",")
            if (parts.size >= 2) {
                val number = parts[0]
                val description = parts[1]
                contacts.add(Triple(number, description, mutableStateOf(false)))
            }
        }

        val composeView = binding.composeViewEmergencyNumber
        composeView.setContent {
            InitComposeContent()
        }

        setContentView(binding.root)
    }

    @Composable
    private fun InitComposeContent() {
        val selectedTabIndex = remember { mutableStateOf(0) }

        val isDialogOpen = remember { mutableStateOf(false) }

        // 수정 중인지 여부
        val isEditing = remember { mutableStateOf(false) }

        CreatePersonalDialog(isDialogOpen) { number, description, isSelected ->
            contacts.add(Triple(number, description, mutableStateOf(false)))
            writeToFile(this@EmergencyNumberActivity, contacts.map { "${it.first},${it.second}" })
        }

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
                                onClick = {
                                    // isEditing이 false일 때만 selectedTabIndex.value를 변경합니다.
                                    if (!isEditing.value) {
                                        selectedTabIndex.value = index
                                    }
                                }
                            )
                        }
                    }
                }

                when (selectedTabIndex.value) {
                    0 -> {
                        val isSelected = remember { mutableStateOf(false) }
                        EmergencyContact(Icons.Default.LocalFireDepartment, "119", "화재", isEditing, isSelected)
                        EmergencyContact(Icons.Default.LocalHospital, "119", "응급실", isEditing, isSelected)
                        EmergencyContact(Icons.Default.LocalPolice, "112", "경찰", isEditing, isSelected)
                        EmergencyContact(Icons.Default.Waves, "122", "해양사고", isEditing, isSelected)
                    }
                    1 -> {

                        AddandDeleteButtons(
                            onAddClick = { isDialogOpen.value = true },
                            onDeleteClick = { isEditing.value = true },
                            onDeleteCompleteClick = {
                                contacts.removeAll { it.third.value }
                                writeToFile(this@EmergencyNumberActivity, contacts.map { "${it.first},${it.second}" })
                                isEditing.value = false
                            },
                            isEditing = isEditing
                        )
                        contacts.forEach { (number, description, isSelected) ->
                            EmergencyContact(Icons.Default.AccountBox, number, description, isEditing, isSelected)
                        }

                    }
                }
            }
        }
    }
}