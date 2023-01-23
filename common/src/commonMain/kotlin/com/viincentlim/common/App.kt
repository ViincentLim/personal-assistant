package com.viincentlim.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun App() {
    var platformNameText by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Column {
            Button(onClick = {
                platformNameText = "Hello, ${platformName}"
            }) {
                Text(platformNameText)
            }
            Text(text)
            TextField(
                value = text,
                singleLine = true,
                onValueChange = {
                    text = it
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            text = ""
                        },
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
            )
        }
    }
}
