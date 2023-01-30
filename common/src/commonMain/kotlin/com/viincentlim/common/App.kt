package com.viincentlim.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viincentlim.common.items.Item
import com.viincentlim.common.items.Todo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.collections.ArrayList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {
    val headerFontSize = 30.sp

    val todos = rememberSaveable { ArrayList<Todo>() } // todo: replace with data persistence in storage and database
    var textFieldValue by rememberSaveable { mutableStateOf("Hello World") }

    val addButtonInteractionSource = remember { MutableInteractionSource() }
    val addButtonCoroutine = rememberCoroutineScope()
    val onAddButtonClick = {
        if (textFieldValue.trim() != "") {
            todos.add(Todo(name = textFieldValue.trim(), deadline = LocalDate.now(), id = ""))
        }
        textFieldValue = ""
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
    fun LazyListScope.addCard(
        headerText: String,
        viewModels: ArrayList<Item>
    ) {
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                    Text(
                        text = headerText,
                        fontWeight = FontWeight.Black,
                        fontSize = headerFontSize,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    viewModels.forEach {
                        it.createView()
                    }
                }
            }
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(bottom = 56.dp)) {
                addCard("Todos", todos as ArrayList<Item>)
            }
            TextField(
                value = textFieldValue,
                singleLine = true,
                onValueChange = {
                    textFieldValue = it
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
                }.background(Color.White).fillMaxWidth().align(Alignment.BottomCenter),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            textFieldValue = ""
                        },
                    ) {
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = "clear task",
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onAddButtonClick()
                        },
                        interactionSource = addButtonInteractionSource
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "add a task",
                        )
                    }
                },
            )
        }
    }
}


