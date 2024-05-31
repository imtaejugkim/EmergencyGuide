package com.example.emergencyguide.EmergencyNumber.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AddandEditButtons(onAddClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onAddClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
        IconButton(onClick = { /* TODO: Edit action */ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}
