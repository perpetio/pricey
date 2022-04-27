package com.perpetio.pricey.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.Dimen
import com.perpetio.pricey.ui.theme.Text

@Composable
fun ProductHeader(
    productArticle: ProductArticle,
    goBack: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(
            bottomStart = Dimen.Corners.main,
            bottomEnd = Dimen.Corners.main
        ),
        elevation = Dimen.Elevation.main
    ) {
        Box(
            modifier = Modifier.padding(Dimen.Space.main),
        ) {
            BackButton(
                modifier = Modifier.align(Alignment.TopStart),
                goBack = goBack
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(productArticle.imageResId),
                    modifier = Modifier
                        .padding(bottom = Dimen.Space.main)
                        .fillMaxWidth()
                        .height(Dimen.Size.productImageBig),
                    contentDescription = "Product image"
                )
                Text(
                    text = productArticle.name,
                    style = Text.Style(Text.Size.Max, AppColors.Orange).value
                )
            }
        }
    }
}