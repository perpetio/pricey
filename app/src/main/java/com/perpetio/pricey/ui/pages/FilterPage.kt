package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.DataProvider
import com.perpetio.pricey.data.ExpirationPeriod
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.theme.*
import com.perpetio.pricey.utils.toPrice

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
        Modifier
            .fillMaxSize()
            .background(AppColors.LightOrange)
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            productArticle = productArticle,
            goBack = goBack
        )
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
        ) {
            PriceFilter(
                filterRange = 7f..18f,
                maxRange = 5f..21f
            )
            Spacer(modifier = Modifier.height(10.dp))
            RatingFilter(
                filterRating = 2,
                maxRating = 5
            )
            Spacer(modifier = Modifier.height(20.dp))
            ExpirationFilter(
                filterExpiration = ExpirationPeriod.UpTo7
            )
            Spacer(modifier = Modifier.height(20.dp))
            ApplyButton(
                modifier = Modifier.fillMaxWidth()
            )
        }
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
    maxRange: ClosedFloatingPointRange<Float>
) {
    var sliderThumbPositions by remember { mutableStateOf(maxRange) }
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.price),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(1 ${stringResource(R.string.kg)})",
                style = Text.Style(Text.Size.Main).value
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SliderLabels(
            values = sliderThumbPositions,
            maxRange = maxRange
        )
        RangeSlider(
            values = sliderThumbPositions,
            valueRange = maxRange,
            onValueChange = {
                sliderThumbPositions = it
            },
            colors = SliderDefaults.colors(
                thumbColor = AppColors.Orange
            )
        )
        Row() {
            Text(
                text = "(${maxRange.start} ${stringResource(R.string.dollar)})",
                style = Text.Style(Text.Size.Main).value
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "(${maxRange.endInclusive} ${stringResource(R.string.dollar)})",
                style = Text.Style(Text.Size.Main).value
            )
        }
    }
}

@Composable
private fun RatingFilter(
    filterRating: Int,
    maxRating: Int
) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.rating),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(${stringResource(R.string.min)})",
                style = Text.Style(Text.Size.Main).value
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
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.expiration),
                style = Text.Style(Text.Size.Title, AppColors.DarkGreen).value,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(${stringResource(R.string.days)})",
                style = Text.Style(Text.Size.Main).value
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
private fun ApplyButton(
    modifier: Modifier
) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.Orange
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.apply),
            style = Text.Style(Text.Size.Bold, Color.White).value
        )
    }
}

@Composable
fun SliderLabels(
    values: ClosedFloatingPointRange<Float>,
    maxRange: ClosedFloatingPointRange<Float>
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val labelMinWidth = 30.dp
        val firstLabelPosition = getLabelPosition(
            values.start, maxRange, labelMinWidth, maxWidth
        )
        val secondLabelPosition = getLabelPosition(
            values.endInclusive, maxRange, labelMinWidth, maxWidth
        )
        if (values.start > maxRange.start) {
            SliderLabel(
                value = values.start,
                viewMinWidth = labelMinWidth,
                viewPosition = firstLabelPosition
            )
        }
        if (values.endInclusive < maxRange.endInclusive) {
            SliderLabel(
                value = values.endInclusive,
                viewMinWidth = labelMinWidth,
                viewPosition = secondLabelPosition
            )
        }
    }
}

@Composable
private fun SliderLabel(
    value: Float,
    viewMinWidth: Dp,
    viewPosition: Dp,
) {
    Text(
        text = "${value.toPrice()} ${stringResource(R.string.dollar)}",
        style = Text.Style(Text.Size.Main).value,
        modifier = Modifier
            .defaultMinSize(minWidth = viewMinWidth)
            .padding(start = viewPosition)
    )
}

private fun getLabelPosition(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    labelMinWidth: Dp,
    sliderWidth: Dp,
): Dp {
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, value)
    return (sliderWidth - labelMinWidth) * positionFraction
}

private fun calcFraction(
    start: Float,
    end: Float,
    pos: Float
): Float {
    return if (end - start != 0f) {
        ((pos - start) / (end - start)).coerceIn(0f, 1f)
    } else 0f
}

@Composable
private fun ExpirationRange(
    periods: List<ExpirationPeriod>,
    selectedPeriod: ExpirationPeriod,
    onSelect: (ExpirationPeriod) -> Unit
) {
    Row {
        periods.forEach { period ->
            Column {
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
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = period.rangeInDays,
                    style = Text.Style(Text.Size.Main).value
                )
            }
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