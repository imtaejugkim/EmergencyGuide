package com.example.emergencyguide.EmergencyGuide

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.emergencyguide.R

@Composable
fun TopBar(onBackPressed: () -> Unit) {
    val pretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically // 아이콘과 제목의 높이를 맞춤
    ) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.width(100.dp))

        Text(
            text = "응급 처치",
            fontFamily = pretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}


@Composable
fun SearchField(searchQuery: TextFieldValue, onSearchQueryChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(end = 8.dp)
            )
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("검색어를 입력하세요.") },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                colors =
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}


@Composable
fun GuideItem(guide: ExpandableBoxData) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 8.dp)
            .padding(horizontal = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // 위와 아래에 공백 추가
        ) {
            Text(
                guide.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_down_button),
                contentDescription = if (expanded) "Collapse" else "Expand",
                modifier = Modifier.size(24.dp)
            )
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                AndroidView(
                    factory = { context ->
                        LinearLayout(context).apply {
                            orientation = LinearLayout.VERTICAL
                            setBackgroundResource(R.drawable.custom_bg1)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = (guide.steps.size * 250).dp), // 세로 높이를 늘림
                    update = { layout ->
                        layout.removeAllViews()
                        val composeView = ComposeView(layout.context).apply {
                            setContent {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = guide.content,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        AndroidView(
                                            factory = { context ->
                                                LinearLayout(context).apply {
                                                    orientation = LinearLayout.HORIZONTAL
                                                    setBackgroundResource(R.drawable.custom_button1)
                                                    setPadding(16, 8, 16, 8)

                                                    val textView = TextView(context).apply {
                                                        text = "자세히 보기"
                                                        textSize = 14f
                                                        setTextColor(
                                                            ContextCompat.getColor(
                                                                context,
                                                                android.R.color.black
                                                            )
                                                        )
                                                    }

                                                    setOnClickListener {
                                                        val intent = Intent(context, DetailActivity::class.java).apply {
                                                            putExtra("title", guide.title)
                                                            putExtra("content", guide.content)
                                                            putExtra("youtubeUrl", guide.youtubeUrl)
                                                        }
                                                        context.startActivity(intent)
                                                    }

                                                    addView(textView)
                                                }
                                            },
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .wrapContentHeight()
                                        )
                                    }
                                    Column {
                                        guide.steps.forEach { step ->
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp) // 최소 padding
                                            ) {
                                                AndroidView(
                                                    factory = { context ->
                                                        ImageView(context).apply {
                                                            setImageDrawable(
                                                                ContextCompat.getDrawable(
                                                                    context,
                                                                    step.imageResId
                                                                )
                                                            )
                                                            scaleType = ImageView.ScaleType.FIT_CENTER
                                                        }
                                                    },
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .heightIn(min = 200.dp) // 각 Step의 최소 높이를 늘림
                                                        .padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp) // 이미지의 패딩
                                                )
                                                Text(
                                                    text = step.title,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 16.dp) // 필요에 따라 패딩 조정
                                                )
                                                Text(
                                                    text = step.description,
                                                    fontSize = 16.sp,
                                                    modifier = Modifier.padding(horizontal = 16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        layout.addView(composeView)
                    }
                )
            }
        }
    }
}


