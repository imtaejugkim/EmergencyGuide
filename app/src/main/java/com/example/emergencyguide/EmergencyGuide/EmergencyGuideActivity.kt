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
import com.example.emergencyguide.ui.theme.pretendardTypography

class EmergencyGuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyGuideBinding.inflate(layoutInflater)

        val composeView = binding.composeViewEmergencyGuide
        composeView.setContent {
            MaterialTheme(
                typography = pretendardTypography, // 여기에 Typography를 설정
                content = {
                    InitComposeContent()
                }
            )
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
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically // 아이콘과 제목의 높이를 맞춤
                ) {
                    IconButton(onClick = { finish() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    Spacer(modifier = Modifier.width(100.dp))

                    Text(text = "긴급 연락처", style = pretendardTypography.h1)

                }

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
                youtubeUrl = "https://www.youtube.com/watch?v=2ZIdOeTZRMk",
                steps = listOf(
                    StepData(R.drawable.first_aid_cpr_1, "Step 1", listOf("심정지 및 무호흡 확인", "양어깨를 두드리며 말을 걸고 눈과 귀로 심정지 및 무호흡 유무를 확인한다.")),
                    StepData(R.drawable.first_aid_cpr_2, "Step 2", listOf("도움 및 119신고 요청", "주변사람에게(꼭 집어서) 119신고를 부탁하고 자동심장충격기를 요청한다.")),
                    StepData(R.drawable.first_aid_cpr_3, "Step 3", listOf("가슴압박 30회 시행", "환자의 가슴 중앙에 깍지낀 두손으로 몸과 수직이 되도록 압박한다.")),
                    StepData(R.drawable.first_aid_cpr_4, "Step 4", listOf("인공호흡 2회 시행", "코를 막고 구조자의 입을 완전히 밀착하여 정상호흡을 약 1초에 걸쳐 2회 숨을 불어 넣는다.")),
                    StepData(R.drawable.first_aid_cpr_5, "Step 5", listOf("가슴압박, 인공호흡 반복", "이후에는 30회의 가슴압박과 2회의 인공호흡을 119구급대원이 현장에 도착할 때까지 반복해서 시행한다."))
                )
            ),
            ExpandableBoxData(
                title = "기도폐쇄/목에 걸림",
                content = "하임리히법(Heimlich)",
                youtubeUrl = "https://www.youtube.com/watch?v=PxP2VArWh94",
                steps = listOf(
                    StepData(R.drawable.first_aid_heimlich_1, "Step 1", listOf("상태체크 및 119신고 요청", "환자가 숨쉬기 힘들어 하거나 목을 감싸 괴로움을 호소할 경우 기도폐쇄로 판단하고 주변에 119에 신고를 요청한다.")),
                    StepData(R.drawable.first_aid_heimlich_2, "Step 2", listOf("하임리히법 실시", "환자의 등 뒤에 서서 주먹을 쥔 손의 엄지손가락 방향을 배 윗부분에 대고 다른 한 손을 위에 겹친 후 환자의 배꼽에서 명치 사이의 배 부위를 두 손으로 위로 쓸어올리듯 강하게 밀어 올려서 이물을 제거하고 이물이 밖으로 나왔는지 확인한다."))
                )
            ),
            ExpandableBoxData(
                title = "열상화상",
                content = "열상화상 응급 처지",
                youtubeUrl = "https://www.youtube.com/watch?v=RIU2i0xVipU",
                steps = listOf(
                    StepData(R.drawable.first_aid_burn_1, "Step 1", listOf("화상 부위를 찬물에 20분 이상 담가 열기를 식힌다.", "뜨거운 액체에 화상을 입은 경우 옷을 벗기지 않고 냉각시킨다.")),
                    StepData(R.drawable.first_aid_burn_2, "Step 2", listOf("물집은 절대 터뜨리지 말고 로션, 된장, 간장, 소주 등도 절대 바르지 않는다", "시계, 반지, 목걸이 등의 장신구는 피부가 부어오르기 전에 최대한 빨리 제거한다.")),
                    StepData(R.drawable.first_aid_burn_3, "Step 3", listOf("화상 부위에 바세린이나 화상 거즈(깨끗한 거즈)로 덮어주고 붕대로 감아준다.", ""))
                )
            ),
            ExpandableBoxData(
                title = "벌에 쏘인 경우",
                content = "독침 제거",
                youtubeUrl = "https://www.youtube.com/watch?v=NAyWuEA2mYA",
                steps = listOf(
                    StepData(R.drawable.first_aid_bee_1, "Step 1", listOf("벌침 찾기", "빨갛게 부어오른 부위에 검은 점처럼 보이는 벌침을 찾는다.")),
                    StepData(R.drawable.first_aid_bee_2, "Step 2", listOf("벌침 제거", "신용카드 등을 이용해 피부를 긁어내듯 침을 제거한다.")),
                    StepData(R.drawable.first_aid_bee_3, "Step 3", listOf("통증(부기) 완화", "상처 부위에 얼음주머니를 대 통증과 부기를 가라앉힌다."))
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
                            putExtra("youtubeUrl", data.youtubeUrl)
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
