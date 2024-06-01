package com.example.emergencyguide.EmergencyGuide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyguide.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val composeView = binding.composeViewDetail
        composeView.setContent {
            InitComposeContent()
        }
        setContentView(binding.root)
        val title = intent.getStringExtra("title") ?: "Detail"
        val content = intent.getStringExtra("content") ?: "No content available"
    }

    @Composable
    fun InitComposeContent() {
        Text("wtf")
    }
}

@Composable
fun DetailScreen(title: String, content: String) {
    val context = LocalContext.current

    Column {
        TopAppBar(
            title = { Text(text = title, fontSize = 18.sp) },
            navigationIcon = {
                IconButton(onClick = { (context as? AppCompatActivity)?.finish() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.Black,
            elevation = 0.dp
        )
        Text(text = content, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
    }
}
