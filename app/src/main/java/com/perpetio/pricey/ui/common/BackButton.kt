package com.perpetio.pricey.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.ui.theme.AppColors

@Composable
fun BackButton(
    modifier: Modifier,
    goBack: () -> Unit
) {
    IconButton(
        modifier = modifier
            .padding(
                start = 10.dp,
                top = 10.dp
            )
            .size(30.dp),
        onClick = { goBack() }
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "Button back",
            colorFilter = ColorFilter.tint(AppColors.Orange),
            modifier = Modifier.size(20.dp)
        )
    }
}