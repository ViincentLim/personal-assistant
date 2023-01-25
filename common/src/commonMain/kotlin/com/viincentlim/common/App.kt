package com.viincentlim.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {
    var platformNameText by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()
    var texts by remember { mutableStateOf(ArrayList<String>()) }
    var text by remember { mutableStateOf("Hello, World!") }

    val addButtonInteractionSource = remember { MutableInteractionSource() }
    val addButtonCoroutine = rememberCoroutineScope()
    val onAddButtonClick = {
        if (text.trim() != "") {
            texts.add(text.trim())
            text = ""
        }
    }
    fun simulateAddButtonPress() {
        addButtonCoroutine.launch {
            val press = PressInteraction.Press(Offset.Zero)
            addButtonInteractionSource.emit(press)
            onAddButtonClick()
            delay(300)
            addButtonInteractionSource.emit(PressInteraction.Release(press))
        }
    }

    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color.Black)) {
        Column {
            Button(onClick = {
                platformNameText = "Hello, ${platformName}"
            }) {
                Text(platformNameText)
            }
            texts.map { p -> Text(p) }
            TextField(
                value = text,
                singleLine = true,
                onValueChange = {
                    text = it
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        simulateAddButtonPress()
                    }
                ),
                modifier = Modifier.onKeyEvent { keyEvent ->
                    println(keyEvent.key)
                    if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                        simulateAddButtonPress()
                    }
                    return@onKeyEvent false
                },
                leadingIcon = {
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
                trailingIcon = {
                    IconButton(
                        onClick = onAddButtonClick,
                        interactionSource = addButtonInteractionSource
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
            )
        }
    }
}

