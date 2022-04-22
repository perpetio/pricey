package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.*
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.*
import java.util.*

@Preview(showBackground = true)
@Composable
private fun Preview() {
    FilterPage(
        DataProvider.productArticles[0],
        {}
    )
}

@Composable
fun FilterPage(
    productArticle: ProductArticle,
    goBack: () -> Unit,
) {
    Column(
        Modifier.background(
            color = AppColors.LightOrange
        )
    ) {
        Header(
            productArticle = productArticle,
            goBack = goBack
        )
        Spacer(modifier = Modifier.height(10.dp))
        PriceFilter(
            filterRange = 7f..18f,
            wholeRange = 5f..21f
        )
        Spacer(modifier = Modifier.height(10.dp))
        RatingFilter(
            filterRating = 2,
            maxRating = 5
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExpirationFilter(
            filterExpiration = ExpirationPeriod.UpTo7
        )
        Spacer(modifier = Modifier.height(10.dp))
        ApplyButton()
    }
}

@Composable
private fun Header(
    productArticle: ProductArticle,
    goBack: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(
            bottomStart = Plate.corners.dp,
            bottomEnd = Plate.corners.dp
        ),
        elevation = Plate.elevation.dp
    ) {
        Box(
            modifier = Modifier.padding(SpaceStyle.main.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Button back",
                colorFilter = ColorFilter.tint(AppColors.Orange),
                modifier = Modifier
                    .size(ButtonStyle.size.dp)
                    .padding(ButtonStyle.padding.dp)
                    .clickable { goBack() }
                    .align(Alignment.TopStart)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(productArticle.imageResId),
                    modifier = Modifier
                        .padding(bottom = SpaceStyle.main.dp)
                        .fillMaxWidth()
                        .height(150.dp),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PriceFilter(
    filterRange: ClosedFloatingPointRange<Float>,
    wholeRange: ClosedFloatingPointRange<Float>
) {
    var sliderPosition by remember { mutableStateOf(wholeRange) }
    Column {
        Row {
            Text(
                text = stringResource(R.string.price),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(1 ${stringResource(R.string.kg)})",
                style = Text.Style(Text.Size.Main).value,
                modifier = Modifier.padding(Plate.padding.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        RangeSlider(
            values = sliderPosition,
            valueRange = wholeRange,
            onValueChange = {
                sliderPosition = it
            },
            colors = SliderDefaults.colors(
                thumbColor = AppColors.Orange
            )
        )
    }
}

@Composable
private fun RatingFilter(
    filterRating: Int,
    maxRating: Int
) {
    Column {
        Row {
            Text(
                text = stringResource(R.string.rating),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(${stringResource(R.string.min)})",
                style = Text.Style(Text.Size.Main).value,
                modifier = Modifier.padding(Plate.padding.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Rating(
            currentValue = filterRating,
            maxValue = maxRating
        )
    }
}

@Composable
private fun ExpirationFilter(
    filterExpiration: ExpirationPeriod
) {
    Column {
        Row {
            Text(
                text = stringResource(R.string.rating),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(${stringResource(R.string.min)})",
                style = Text.Style(Text.Size.Main).value,
                modifier = Modifier.padding(Plate.padding.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        val periods = ExpirationPeriod.values().toList()
        var selectedPeriod by remember { mutableStateOf(periods.first()) }
        ExpirationRange(
            periods = periods,
            selectedPeriod = selectedPeriod,
            onSelect = { period ->
                selectedPeriod = period
            }
        )
    }
}

@Composable
private fun ApplyButton() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.Orange
        )
    ) {
        Text(
            text = stringResource(R.string.apply),
            style = Text.Style(Text.Size.Bold, Color.White).value
        )
    }
}

@Composable
private fun ExpirationRange(
    periods: List<ExpirationPeriod>,
    selectedPeriod: ExpirationPeriod,
    onSelect: (ExpirationPeriod) -> Unit
) {
    Row {
        periods.forEach { period ->
            RadioButton(
                selected = (period == selectedPeriod),
                onClick = { onSelect(period) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = AppColors.Orange,
                    unselectedColor = AppColors.Orange
                ),
                modifier = Modifier
                    .height(20.dp)
                    .padding(end = 20.dp)
            )
        }
    }
}

@Composable
private fun Rating(
    currentValue: Int,
    maxValue: Int
) {
    Row {
        for (tmpValue in 1..maxValue) {
            Image(
                painter = painterResource(
                    if (tmpValue <= currentValue) R.drawable.ic_start
                    else R.drawable.ic_unstart
                ),
                colorFilter = ColorFilter.tint(AppColors.Orange),
                contentDescription = "Rating star",
                modifier = Modifier
                    .height(20.dp)
                    .padding(end = 20.dp)
            )
        }
    }
}