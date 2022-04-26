package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.perpetio.pricey.R
import com.perpetio.pricey.data.ExpirationPeriod
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.common.BackButton
import com.perpetio.pricey.ui.theme.AppColors
import com.perpetio.pricey.ui.theme.Plate
import com.perpetio.pricey.ui.theme.SpaceStyle
import com.perpetio.pricey.ui.theme.Text
import com.perpetio.pricey.utils.toPrice

@Composable
fun FilterPage(
    productArticle: ProductArticle,
    priceRange: ClosedFloatingPointRange<Float>,
    priceFilter: ClosedFloatingPointRange<Float>,
    ratingValues: IntRange,
    ratingFilter: Int,
    expirationValues: List<ExpirationPeriod>,
    expirationFilter: ExpirationPeriod,
    onPriceRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onRatingChange: (Int) -> Unit,
    onExpirationPeriodChange: (ExpirationPeriod) -> Unit,
    onApplyFilters: () -> Unit,
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
                filterRange = priceFilter,
                maxRange = priceRange,
                onRangeChange = onPriceRangeChange
            )
            Spacer(modifier = Modifier.height(10.dp))
            RatingFilter(
                filterValue = ratingFilter,
                rangeValues = ratingValues,
                onValueChange = onRatingChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            ExpirationFilter(
                filterValue = expirationFilter,
                allValues = expirationValues,
                onValueChange = onExpirationPeriodChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            ApplyButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onApplyFilters
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
    maxRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit
) {
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
            values = filterRange,
            maxRange = maxRange
        )
        RangeSlider(
            values = filterRange,
            valueRange = maxRange,
            onValueChange = onRangeChange,
            colors = SliderDefaults.colors(
                thumbColor = AppColors.Orange
            )
        )
        Row {
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
    filterValue: Int,
    rangeValues: IntRange,
    onValueChange: (Int) -> Unit
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
            selectedValue = filterValue,
            rangeValues = rangeValues,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun ExpirationFilter(
    filterValue: ExpirationPeriod,
    allValues: List<ExpirationPeriod>,
    onValueChange: (ExpirationPeriod) -> Unit
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
        ExpirationRange(
            periods = allValues,
            selectedPeriod = filterValue,
            onValueChange = onValueChange
        )
    }
}

@Composable
private fun ApplyButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Plate.corners.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.Orange
        )
    ) {
        Text(
            text = stringResource(R.string.apply),
            style = Text.Style(Text.Size.Bold, Color.White).value,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(5.dp)
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
    onValueChange: (ExpirationPeriod) -> Unit
) {
    Row {
        periods.forEach { period ->
            Column {
                RadioButton(
                    selected = (period == selectedPeriod),
                    onClick = { onValueChange(period) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.Orange,
                        unselectedColor = AppColors.Orange
                    ),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = period.rangeInDays,
                    style = Text.Style(Text.Size.Main).value
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Composable
private fun Rating(
    selectedValue: Int,
    rangeValues: IntRange,
    onValueChange: (Int) -> Unit
) {
    Row {
        for (value in rangeValues) {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { onValueChange(value) }
            ) {
                Image(
                    painter = painterResource(
                        if (value <= selectedValue) R.drawable.ic_start
                        else R.drawable.ic_unstart
                    ),
                    colorFilter = ColorFilter.tint(AppColors.Orange),
                    contentDescription = "Rating star",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}