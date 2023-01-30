package com.viincentlim.common.items

import androidx.compose.runtime.Composable

// TODO generate id automatically
open class Item(open val id: String) {
    @Composable
    open fun createView() {}
}