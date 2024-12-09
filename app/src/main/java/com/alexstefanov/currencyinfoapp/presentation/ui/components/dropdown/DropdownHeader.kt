package com.alexstefanov.currencyinfoapp.presentation.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexstefanov.currencyinfoapp.R

@Composable
fun DropdownHeader(
    selectedCurrency: String,
    arrowRotationAngle: Float,
    onHeaderClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .defaultMinSize(minHeight = 48.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .clickable { onHeaderClick() }
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
}