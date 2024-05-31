package com.example.emergencyguide.EmergencyNumber.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AddandDeleteButtons(onAddClick: () -> Unit,
                        onDeleteClick: () -> Unit,
                        onDeleteCompleteClick: () -> Unit,
                        isEditing: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 수정 중일 때(삭제 버튼만)
        if (isEditing.value) {
            IconButton(onClick = onDeleteCompleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }

        // 수정 중이 아닐 때
        else {
            IconButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }

    }
}
