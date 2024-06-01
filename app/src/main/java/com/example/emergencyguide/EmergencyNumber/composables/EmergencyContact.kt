package com.example.emergencyguide.EmergencyNumber.composables

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
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EmergencyContact(icon: androidx.compose.ui.graphics.vector.ImageVector,
                     number: String,
                     title: String,
                     isEditing: MutableState<Boolean>,
                     ) {

    val isSelected = remember { mutableStateOf(false) }

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
            if (isEditing.value) {
                Checkbox(
                    checked = isSelected.value,
                    onCheckedChange = { newValue -> isSelected.value = newValue }
                )
            } else {
                IconButton(onClick = { /* call action 적기 */ }) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                }
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.align(Alignment.BottomCenter))
    }
}