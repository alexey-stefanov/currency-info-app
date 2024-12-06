package com.alexstefanov.currencyinfoapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexstefanov.currencyinfoapp.R

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: () -> Unit) {
    Column {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )
        NavigationBar {
            NavigationBarItem(
                selected = selectedTab == stringResource(R.string.currencies),
                onClick = {},
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_currencies),
                        contentDescription = stringResource(R.string.currencies)
                    )
                },
                label = {
                    Text(
                        stringResource(R.string.currencies),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
            NavigationBarItem(
                selected = selectedTab == stringResource(R.string.favorites),
                onClick = {},
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_favorites),
                        contentDescription = stringResource(R.string.favorites)
                    )
                },
                label = {
                    Text(
                        stringResource(R.string.favorites),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
