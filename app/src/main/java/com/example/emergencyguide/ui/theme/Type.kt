package com.example.emergencyguide.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.emergencyguide.R

val pretendardFamily = FontFamily(
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_thin, FontWeight.Thin),
)

//// Set of Material typography styles to start with
//val pretendardTypography = Typography(
//    bodyLarge = TextStyle(
////        fontFamily = FontFamily.Default,
//        fontFamily = pretendardFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
//)

// Jetpack Compose의 Material 3는 Material 2와는 다른 API를 사용하므로, 두 버전 간에는 호환성이 없기 때문
// bodyLarge -> body1으로 수정
val pretendardTypography = androidx.compose.material.Typography(
    body1 = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    
    // 제목
    h1 = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
)
