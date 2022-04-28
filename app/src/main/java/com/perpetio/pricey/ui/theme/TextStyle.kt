package com.perpetio.pricey.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Text {
    class Style(
        textSize: Size,
        color: Color,
        fontWeight: FontWeight? = null,
    ) {
        val value = TextStyle(
            color = color,
            fontSize = textSize.value.sp,
            fontWeight = fontWeight ?: getFontWeight(textSize)
        )

        private fun getFontWeight(
            textSize: Size
        ): FontWeight {
            return when (textSize) {
                Size.Small -> FontWeight.Bold
                Size.Main -> FontWeight.Medium
                Size.Bold,
                Size.Title,
                Size.Max -> FontWeight.ExtraBold
            }
        }
    }

    enum class Size(
        val value: Int
    ) {
        Small(12),
        Main(14),
        Bold(16),
        Title(18),
        Max(22)
    }
}