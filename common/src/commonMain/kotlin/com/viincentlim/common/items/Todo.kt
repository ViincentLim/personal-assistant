package com.viincentlim.common.items

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import java.time.LocalDate

class Todo(var name: String, var deadline: LocalDate, override val id: String) : Item(id) {
    @Composable
    override fun createView() {
        Text(name)
    }
}