package com.alexstefanov.currencyinfoapp.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.alexstefanov.currencyinfoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(
    modifier: Modifier = Modifier,
    selectedCurrency: String,
    currencyList: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var dropdownWidth by remember { mutableIntStateOf(0) }

    val arrowRotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Dropdown Arrow Animation"
    )

    Box(
        modifier = modifier
            .wrapContentWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .onGloballyPositioned { coordinates ->
                    dropdownWidth = coordinates.size.width
                }
                .clip(RoundedCornerShape(size = 8.dp))
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCurrency,
                style = MaterialTheme.typography.bodySmall
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "Dropdown Arrow",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.rotate(arrowRotationAngle)
            )
        }

        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false }
            ) {

                Column(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { dropdownWidth.toDp() })
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .heightIn(max = (48 + (56 * 3)).dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .defaultMinSize(minHeight = 48.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .clickable {
                                expanded = !expanded
                            }
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedCurrency,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = "Dropdown Arrow",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.rotate(arrowRotationAngle)
                        )
                    }

                    currencyList.forEach { currency ->
                        val currencyItemModifier = if (currency == currencyList.last())
                            Modifier.clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        else
                            Modifier

                        Box(
                            modifier = currencyItemModifier
                                .fillMaxWidth()
                                .background(
                                    color = if (currency == selectedCurrency)
                                        MaterialTheme.colorScheme.secondaryContainer
                                    else
                                        MaterialTheme.colorScheme.background
                                )
                                .defaultMinSize(minHeight = 56.dp)
                                .clickable {
                                    onCurrencySelected(currency)
                                    expanded = false
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = currency,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}

