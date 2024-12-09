package com.alexstefanov.currencyinfoapp.presentation.ui.components.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

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
        DropdownTrigger(
            selectedCurrency = selectedCurrency,
            dropdownWidth = { dropdownWidth = it },
            onTriggerClick = { expanded = !expanded },
            arrowRotationAngle = arrowRotationAngle
        )

        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false }
            ) {

                LazyColumn(
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
                ) {
                    item {
                        DropdownHeader(
                            selectedCurrency = selectedCurrency,
                            arrowRotationAngle = arrowRotationAngle
                        ) {
                            expanded = !expanded
                        }
                    }

                    items(currencyList) { currency ->
                        val currencyItemModifier = if (currency == currencyList.last())
                            Modifier.clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        else
                            Modifier

                        DropdownItem(
                            modifier = currencyItemModifier,
                            currency = currency,
                            isSelected = currency == selectedCurrency,
                            onClick = {
                                onCurrencySelected(currency)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

