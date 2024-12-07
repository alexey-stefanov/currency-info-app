package com.alexstefanov.currencyinfoapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexstefanov.currencyinfoapp.R
import com.alexstefanov.currencyinfoapp.presentation.ui.components.AppTopBar
import com.alexstefanov.currencyinfoapp.presentation.ui.components.CurrencyDropdown
import com.alexstefanov.currencyinfoapp.presentation.ui.components.FilterButton

@Composable
fun CurrenciesScreen(onFilterClick: () -> Unit) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.currencies))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                var selectedCurrency by remember { mutableStateOf("USD") }
                val currencyList = listOf("USD", "BYN", "JYP", "EUR")

                CurrencyDropdown(
                    modifier = Modifier.weight(1f),
                    selectedCurrency = selectedCurrency,
                    currencyList = currencyList
                ) {
                    selectedCurrency = it
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilterButton(onClick = onFilterClick)
            }
        }
    }
}