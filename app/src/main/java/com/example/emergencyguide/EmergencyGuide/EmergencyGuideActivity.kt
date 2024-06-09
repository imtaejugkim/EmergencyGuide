package com.example.emergencyguide.EmergencyGuide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.ActivityEmergencyGuideBinding
import com.example.emergencyguide.ui.theme.pretendardTypography

class EmergencyGuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val composeView = binding.composeViewEmergencyGuide
        composeView.setContent {
            MaterialTheme(
                typography = pretendardTypography,
                content = {
                    InitComposeContent()
                }
            )
        }
    }

    @Composable
    fun InitComposeContent() {
        val scrollState = rememberScrollState()
        val context = LocalContext.current
        val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

        val guideData = listOf(
            // 데이터 모델을 초기화합니다.
            ExpandableBoxData(
                title = "심정지",
                content = "심폐소생술(CPR)",
                youtubeUrl = "https://www.youtube.com/watch?v=2ZIdOeTZRMk",
                steps = listOf(
                    StepData(
                        R.drawable.first_aid_cpr_1,
                        "Step 1: 상태체크 및 119신고 요청",
                        "환자가 의식이 없고 숨을 쉬지 않는다면 즉시 119에 신고하고 도움을 요청한다."
                    ),
                    StepData(
                        R.drawable.first_aid_cpr_2,
                        "Step 2: 흉부압박 실시",
                        "양 손을 포개어 환자의 흉골 중앙에 놓고 체중을 실어 강하고 빠르게 30회의 흉부압박을 실시한다."
                    ),
                    StepData(
                        R.drawable.first_aid_cpr_3,
                        "Step 3: 기도확보",
                        "환자의 머리를 젖혀 기도를 확보하고, 호흡이 정상적으로 돌아오는지 확인한다."
                    ),
                    StepData(
                        R.drawable.first_aid_cpr_4,
                        "Step 4: 인공호흡 실시",
                        "환자의 코를 막고 자신의 입을 환자의 입에 밀착시켜 2회의 인공호흡을 실시한다."
                    ),
                    StepData(
                        R.drawable.first_aid_cpr_5,
                        "Step 5: 반복",
                        "119가 도착할 때까지 흉부압박과 인공호흡을 반복 실시한다."
                    )
                )
            ),
            ExpandableBoxData(
                title = "기도폐쇄/목에 걸림",
                content = "하임리히법(Heimlich)",
                youtubeUrl = "https://www.youtube.com/watch?v=PxP2VArWh94",
                steps = listOf(
                    StepData(
                        R.drawable.first_aid_heimlich_1,
                        "Step 1: 상태체크 및 119신고 요청",
                        "환자가 숨쉬기 힘들어 하거나 목을 감싸 괴로움을 호소할 경우 기도폐쇄로 판단하고 주변에 119에 신고를 요청한다."
                    ),
                    StepData(
                        R.drawable.first_aid_heimlich_2,
                        "Step 2: 하임리히법 실시",
                        "환자의 등 뒤에 서서 주먹을 쥔 손의 엄지손가락 방향을 배 윗부분에 대고 다른 한 손을 위에 겹친 후 환자의 배꼽에서 명치 사이의 배 부위를 두 손으로 위로 쓸어올리듯 강하게 밀어 올려서 이물을 제거하고 이물이 밖으로 나왔는지 확인한다."
                    )
                )
            ),
            // 나머지 데이터를 여기에 추가하세요.
            ExpandableBoxData(
                title = "열상화상",
                content = "화상 응급처치",
                youtubeUrl = "https://www.youtube.com/watch?v=RIU2i0xVipU&t",
                steps = listOf(
                    StepData(
                        R.drawable.first_aid_burn_1,
                        "Step 1",
                        "화상 부위를 차가운 물로 식혀줍니다."
                    ),
                    StepData(
                        R.drawable.first_aid_burn_2,
                        "Step 2",
                        "화상 부위를 깨끗한 천으로 덮어줍니다."
                    ),
                    StepData(
                        R.drawable.first_aid_burn_3,
                        "Step 3",
                        "병원에 도착할 때까지 환자를 안심시킵니다."
                    )
                )
            ),
            ExpandableBoxData(
                title = "벌에 쏘인 경우",
                content = "벌 쏘임 응급처치",
                youtubeUrl = "https://www.youtube.com/watch?v=D-lC-ralnKg",
                steps = listOf(
                    StepData(R.drawable.first_aid_bee_1, "Step 1", "쏘인 부위를 깨끗이 씻어줍니다."),
                    StepData(R.drawable.first_aid_bee_2, "Step 2", "얼음찜질로 부기를 줄여줍니다."),
                    StepData(R.drawable.first_aid_bee_3, "Step 3", "상태가 심각하면 즉시 병원에 갑니다.")
                )
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 30.dp)
                .verticalScroll(scrollState)
        ) {
            TopBar {
                // Activity를 종료하여 이전 화면으로 돌아가기
                if (context is AppCompatActivity) {
                    context.finish()
                }
            }
            SearchField(
                searchQuery = searchQuery.value,
                onSearchQueryChange = { searchQuery.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val filteredData = guideData.filter { guide ->
                guide.title.contains(searchQuery.value.text, ignoreCase = true) ||
                        guide.content.contains(searchQuery.value.text, ignoreCase = true)
            }

            filteredData.forEach { guide ->
                GuideItem(guide)
                Divider()
            }
        }
    }
}