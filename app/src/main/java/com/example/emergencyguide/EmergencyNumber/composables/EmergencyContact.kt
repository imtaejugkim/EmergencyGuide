package com.example.emergencyguide.EmergencyNumber.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContact(icon: androidx.compose.ui.graphics.vector.ImageVector,
                     number: String,
                     title: String,
                     isEditing: MutableState<Boolean>,
                     isSelected: MutableState<Boolean> // isSelected 상태를 인자로 추가
)  {

    val context = LocalContext.current // 현재 Context를 가져옵니다.
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.CALL_PHONE)


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
                IconButton(onClick = { // 전화 앱을 시작합니다.

                    if(permissionState.status.isGranted){
                        val number = Uri.parse("tel:$number")
                        val callIntent = Intent(Intent.ACTION_CALL, number)
                        context.startActivity(callIntent)
                    }

                    else
                        permissionState.launchPermissionRequest()

                     }) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                }
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

