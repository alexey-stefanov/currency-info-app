package com.alexstefanov.currencyinfoapp.presentation.ui.components.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexstefanov.currencyinfoapp.domain.model.SortOrder

@Composable
fun FiltersRadioButtonGroup(
    selectedSortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SortOrder.entries.forEach { sortOrder ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSortOrderSelected(sortOrder) }
            ) {
                Text(stringResource(sortOrder.filterLabelRes))

                RadioButton(
                    selected = selectedSortOrder == sortOrder,
                    onClick = { onSortOrderSelected(sortOrder) }
                )
            }
        }
    }
}