package com.example.emergencyguide.EmergencyNumber.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CreatePersonalDialog(isDialogOpen: MutableState<Boolean>, onAddContact: (String, String, Boolean) -> Unit) {
    val errorState = remember { mutableStateOf("") }

    if (isDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isDialogOpen.value = false },
            text = {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Text(
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = Color.Black,
                        text = "전화번호 추가"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    val number = remember { mutableStateOf("") }
                    val description = remember { mutableStateOf("") }

                    TextField(
                        value = number.value,
                        onValueChange = { newValue ->
                            // 숫자만 허용하고 빈칸 입력을 허용하지 않음
                            if (newValue.all { it.isDigit() } && newValue.isNotBlank()) {
                                number.value = newValue
                            }
                        },
                        visualTransformation = VisualTransformation.None,
                        label = { Text("전화번호") },
                        modifier = Modifier.padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
                    )
                    TextField(
                        value = description.value,
                        onValueChange = { newValue ->
                            // 빈칸 입력을 허용하지 않음
                            if (newValue.isNotBlank()) {
                                description.value = newValue
                            }
                        },
                        label = { Text("설명") },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 오류 메시지 표시
                    if (errorState.value.isNotBlank()) {
                        Text(color = Color.Red, text = errorState.value)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                if (number.value.isBlank() || description.value.isBlank()) {
                                    errorState.value = "전화번호나 설명을 입력해주세요."
                                } else {
                                    onAddContact(number.value, description.value, false)
                                    isDialogOpen.value = false
                                    errorState.value = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                        ) {
                            Text("추가하기", color = Color.White)
                        }
                    }
                }
            },
            confirmButton = { }
        )
    }
}
