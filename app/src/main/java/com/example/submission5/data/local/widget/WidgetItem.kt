package com.example.submission5.data.local.widget

data class WidgetItem(
    val id: Int,
    val imagePath: String? = null,
    val title: String,
    var typeWidget: String = ""
)