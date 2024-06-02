package com.example.emergencyguide.EmergencyGuide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.ActivityEmergencyGuideBinding

class EmergencyGuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyGuideBinding.inflate(layoutInflater)

        val composeView = binding.composeViewEmergencyGuide
        composeView.setContent {
            InitComposeContent()
        }
        setContentView(binding.root)
    }

    @Composable
    fun InitComposeContent() {
        val selectedTabIndex = remember { mutableStateOf(0) }
        val expandedBoxIndex = remember { mutableStateOf<Int?>(null) }
        var query by remember { mutableStateOf("") }

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
                    title = { Text(text = "응급 처치") },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                    elevation = 0.dp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("응급 처치 방법 검색") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_main_search), // Replace with your search icon resource
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                // Search action
                                // Implement the search logic here
                            }
                    )
                }
                AccordionButtons(expandedBoxIndex, query)
            }
        }
    }

    @Composable
    fun AccordionButtons(expandedBoxIndex: MutableState<Int?>, query: String) {
        val boxData = listOf(
            ExpandableBoxData(
                title = "심정지",
                content = "심폐소생술(CPR)",
                steps = listOf(
                    StepData(R.drawable.ic_earthquake, "CPR Step 1", listOf("Description 1.1", "Description 1.2")),
                    StepData(R.drawable.ic_earthquake, "CPR Step 2", listOf("Description 2.1", "Description 2.2"))
                )
            ),
            ExpandableBoxData(
                title = "기도폐쇄/목에 걸림",
                content = "하임리히법(Heimlich)",
                steps = listOf(
                    StepData(R.drawable.ic_earthquake, "Heimlich Step 1", listOf("Description 1.1", "Description 1.2")),
                    StepData(R.drawable.ic_earthquake, "Heimlich Step 2", listOf("Description 2.1", "Description 2.2")),
                    StepData(R.drawable.ic_earthquake, "Heimlich Step 3", listOf("Description 3.1", "Description 3.2"))
                )
            ),
            ExpandableBoxData(
                title = "화상",
                content = "화상 치료법",
                steps = listOf(
                    StepData(R.drawable.ic_earthquake, "Burn Step 1", listOf("Description 1.1", "Description 1.2"))
                )
            )
        )

        val filteredBoxData = boxData.filter { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            filteredBoxData.forEachIndexed { index, data ->
                ExpandableBox(
                    data = data,
                    isExpanded = expandedBoxIndex.value == index,
                    onExpand = { expandedBoxIndex.value = if (expandedBoxIndex.value == index) null else index }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun ExpandableBox(data: ExpandableBoxData, isExpanded: Boolean, onExpand: () -> Unit) {
        val context = LocalContext.current

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .clickable { onExpand() }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = data.title, fontSize = 18.sp)
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
            if (isExpanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = data.content, modifier = Modifier.weight(1f))
                    Button(onClick = {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("title", data.title)
                            putExtra("content", data.content)
                        }
                        context.startActivity(intent)
                    }) {
                        Text(text = "자세히 보기 >")
                    }
                }
                data.steps.forEach { step ->
                    Step(stepData = step)
                }
            }
        }
    }

    @Composable
    fun Step(stepData: StepData) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = stepData.imageRes),
                    contentDescription = "Step Image",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = stepData.title)
                    stepData.description.forEach { line ->
                        Text(text = line)
                    }
                }
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
