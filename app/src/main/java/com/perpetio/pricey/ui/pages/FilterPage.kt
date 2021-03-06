package com.perpetio.pricey.ui.pages

import androidx.compose.foundation.Image
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
import com.perpetio.pricey.R
import com.perpetio.pricey.data.ExpirationPeriod
import com.perpetio.pricey.models.ProductArticle
import com.perpetio.pricey.ui.common.ProductHeader
import com.perpetio.pricey.ui.theme.Dimen
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
            .verticalScroll(rememberScrollState())
    ) {
        ProductHeader(
            productArticle = productArticle,
            goBack = goBack
        )
        Column(
            modifier = Modifier.padding(
                top = Dimen.Space.main,
                bottom = Dimen.Space.max,
                start = Dimen.Space.max,
                end = Dimen.Space.max
            )
        ) {
            PriceFilter(
                filterRange = priceFilter,
                maxRange = priceRange,
                onRangeChange = onPriceRangeChange
            )
            Spacer(modifier = Modifier.height(Dimen.Space.main))
            RatingFilter(
                filterValue = ratingFilter,
                rangeValues = ratingValues,
                onValueChange = onRatingChange
            )
            Spacer(modifier = Modifier.height(Dimen.Space.max))
            ExpirationFilter(
                filterValue = expirationFilter,
                allValues = expirationValues,
                onValueChange = onExpirationPeriodChange
            )
            Spacer(modifier = Modifier.height(Dimen.Space.max))
            ApplyButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onApplyFilters
            )
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
                style = Text.Style(
                    textSize = Text.Size.Title,
                    color = MaterialTheme.colors.secondary
                ).value,
            )
            Spacer(modifier = Modifier.width(Dimen.Space.main))
            Text(
                text = "(1 ${stringResource(R.string.kg)})",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary
                ).value
            )
        }
        Spacer(modifier = Modifier.height(Dimen.Space.main))
        SliderLabels(
            values = filterRange,
            maxRange = maxRange
        )
        RangeSlider(
            values = filterRange,
            valueRange = maxRange,
            onValueChange = onRangeChange
        )
        Row {
            Text(
                text = "(${maxRange.start} ${stringResource(R.string.dollar)})",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary
                ).value
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "(${maxRange.endInclusive} ${stringResource(R.string.dollar)})",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary
                ).value
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
                style = Text.Style(Text.Size.Title, MaterialTheme.colors.secondary).value,
            )
            Spacer(modifier = Modifier.width(Dimen.Space.main))
            Text(
                text = "(${stringResource(R.string.min)})",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary
                ).value
            )
        }
        Spacer(modifier = Modifier.height(Dimen.Space.main))
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
                style = Text.Style(Text.Size.Title, MaterialTheme.colors.secondary).value,
            )
            Spacer(modifier = Modifier.width(Dimen.Space.main))
            Text(
                text = "(${stringResource(R.string.days)})",
                style = Text.Style(
                    textSize = Text.Size.Main,
                    color = MaterialTheme.colors.onPrimary
                ).value
            )
        }
        Spacer(modifier = Modifier.height(Dimen.Space.main))
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
        shape = RoundedCornerShape(Dimen.Corners.main)
    ) {
        Text(
            text = stringResource(R.string.apply),
            style = Text.Style(
                textSize = Text.Size.Bold,
                color = MaterialTheme.colors.background
            ).value,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(Dimen.Padding.small)
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
        val labelMinWidth = Dimen.Size.sliderLabel
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
        style = Text.Style(
            textSize = Text.Size.Main,
            color = MaterialTheme.colors.onPrimary
        ).value,
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (period == selectedPeriod),
                    onClick = { onValueChange(period) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.size(Dimen.Size.button)
                )
                Spacer(modifier = Modifier.height(Dimen.Space.small))
                Text(
                    text = if (period == ExpirationPeriod.Any) {
                        stringResource(R.string.any)
                    } else period.toString(),
                    style = Text.Style(
                        textSize = Text.Size.Main,
                        color = MaterialTheme.colors.onPrimary
                    ).value
                )
            }
            Spacer(modifier = Modifier.width(Dimen.Space.max))
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
                modifier = Modifier.size(Dimen.Size.button),
                onClick = { onValueChange(value) }
            ) {
                Image(
                    painter = painterResource(
                        if (value <= selectedValue) R.drawable.ic_start
                        else R.drawable.ic_unstart
                    ),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = "Rating star",
                    modifier = Modifier.size(Dimen.Size.iconBig)
                )
            }
            Spacer(modifier = Modifier.width(Dimen.Space.max))
        }
    }
}