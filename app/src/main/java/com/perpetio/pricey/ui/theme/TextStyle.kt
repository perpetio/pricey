package com.perpetio.pricey.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class TextStyle(
    color: Color = Color.Black
) {
    val small = TextStyle(
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
    val main = TextStyle(
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
    val title = TextStyle(
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold
    )
    val big = TextStyle(
        color = color,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold
    )
}